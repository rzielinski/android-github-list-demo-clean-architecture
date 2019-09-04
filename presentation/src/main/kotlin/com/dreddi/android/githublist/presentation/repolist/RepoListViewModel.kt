package com.dreddi.android.githublist.presentation.repolist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import com.dreddi.android.githublist.domain.interactor.GetTopRepositories
import com.dreddi.android.githublist.presentation.app.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private val getTopRepositories: GetTopRepositories
) : BaseViewModel() {

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

    fun isData() = repoList.value?.size != 0

    fun isLoading() = isLoading.value == true

    fun getIsLoadingLiveData() = isLoading as LiveData<Boolean>

    fun getRepoListLiveData() = repoList as LiveData<MutableList<RepoEntity>>

    fun fetchRepoList() {
        addDisposable(
                getTopRepositories.getTopRepositories(page, perPage)
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
                        }))
    }

    private fun appendData(repoListEntity: RepoListEntity) {
        val newRepoList = repoList.value
        repoListEntity.repoDataItemsList?.forEach { repo ->
            newRepoList?.add(repo)
        }
        repoList.value = newRepoList
    }
}