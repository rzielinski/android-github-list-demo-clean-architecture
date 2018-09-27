package com.dreddi.android.githublist.presentation.repolist

import android.arch.lifecycle.MutableLiveData
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import com.dreddi.android.githublist.domain.interactor.GetTopRepositories
import com.dreddi.android.githublist.presentation.app.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(
        private var getTopRepositories: GetTopRepositories): BaseViewModel() {

    var page: Int = 0
    var perPage: Int = 10
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var repoList: MutableLiveData<RepoListEntity> = MutableLiveData<RepoListEntity>()

    init {
        isLoading.value = false
    }

    fun isData(): Boolean {
        return repoList.value != null
    }

    fun fetchRepoList() {
        isLoading.value = true
        addDisposable(
                getTopRepositories.getTopRepositories(page, perPage)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    repoList.value = it
                                    isLoading.value = false
                                },
                                {

                                }
                        ))
    }
}