package com.dreddi.android.githublist.presentation.repodetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.app.BaseViewModel

class RepoDetailsViewModel : BaseViewModel() {

    private val repo: MutableLiveData<RepoEntity> = MutableLiveData()

    fun getRepo(): RepoEntity? {
        return repo.value
    }

    fun setRepo(repo: RepoEntity) {
        this.repo.value = repo
    }

    fun getRepoLiveData() = repo as LiveData<RepoEntity>
}