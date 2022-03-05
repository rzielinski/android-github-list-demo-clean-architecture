package com.dreddi.android.githublist.presentation.repolist

import androidx.lifecycle.ViewModel
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import com.dreddi.android.githublist.domain.usecase.GetTopRepositoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepoListViewModel(
        private val getTopRepositoriesUseCase: GetTopRepositoriesUseCase
) : ViewModel() {

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val repoList: MutableStateFlow<MutableList<RepoEntity>> = MutableStateFlow(mutableListOf())

    private var page: Int = 1
    private var perPage: Int = 10

    private val disposables = CompositeDisposable()

    init {
        fetchRepoList()
    }

    override fun onCleared() {
        disposables.clear()
    }

    fun isLoading() = isLoading.value

    fun isLoadingFlow(): StateFlow<Boolean> = isLoading

    fun repoListFlow(): StateFlow<MutableList<RepoEntity>> = repoList

    fun fetchRepoList() {
        getTopRepositoriesUseCase.getTopRepositories(page, perPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoading.value = true
                }
                .subscribe({
                    appendData(it)
                    isLoading.value = false
                    page++
                }, {
                    isLoading.value = false
                }).let { disposables.add(it) }
    }

    private fun appendData(repoListEntity: RepoListEntity) {
        val newRepoList = repoList.value.toMutableList()
        repoListEntity.repoDataItemsList?.let {
            newRepoList.addAll(it)
        }
        repoList.value = newRepoList
    }
}