package com.dreddi.android.githublist.presentation.repolist

import android.arch.lifecycle.MutableLiveData
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import com.dreddi.android.githublist.domain.interactor.GetTopRepositories
import com.dreddi.android.githublist.presentation.app.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private var getTopRepositories: GetTopRepositories): BaseViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var repoList: MutableLiveData<MutableList<RepoEntity>> = MutableLiveData()

    private var page: Int = 0
    private var perPage: Int = 10

    init {
        isLoading.value = false
        repoList.value = mutableListOf<RepoEntity>()
    }

    fun isData(): Boolean {
        return repoList.value?.size != 0
    }

    fun fetchRepoList() {
        addDisposable(
                getTopRepositories.getTopRepositories(page, perPage)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            isLoading.value = true
                        }
                        .subscribe(
                                {
                                    appendData(it)
                                    isLoading.value = false
                                    page++
                                },
                                {
                                    isLoading.value = false
                                }
                        ))
    }

    private fun appendData(repoListEntity: RepoListEntity) {
        var newRepoList = repoList.value
        repoListEntity.repoDataItemsList?.forEach {
            newRepoList?.add(it)
        }
        repoList.value = newRepoList
    }
}