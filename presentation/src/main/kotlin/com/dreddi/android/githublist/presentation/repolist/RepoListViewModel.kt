package com.dreddi.android.githublist.presentation.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import com.dreddi.android.githublist.domain.usecase.GetTopRepositoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private val getTopRepositoriesUseCase: GetTopRepositoriesUseCase
) : ViewModel() {

    private val isLoading: MutableLiveData<Boolean> =
            MutableLiveData<Boolean>().apply {
                value = false
            }

    private val repoList: MutableLiveData<MutableList<RepoEntity>> =
            MutableLiveData<MutableList<RepoEntity>>().apply {
                value = mutableListOf()
            }

    private var page: Int = 1
    private var perPage: Int = 10

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    fun isData() = repoList.value?.size != 0

    fun isLoading() = isLoading.value == true

    fun getIsLoadingLiveData() = isLoading as LiveData<Boolean>

    fun getRepoListLiveData() = repoList as LiveData<MutableList<RepoEntity>>

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
        val newRepoList = repoList.value
        repoListEntity.repoDataItemsList?.forEach { repo ->
            newRepoList?.add(repo)
        }
        repoList.value = newRepoList
    }
}