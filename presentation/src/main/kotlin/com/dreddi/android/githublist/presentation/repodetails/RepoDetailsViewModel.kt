package com.dreddi.android.githublist.presentation.repodetails

import androidx.lifecycle.ViewModel
import com.dreddi.android.githublist.domain.entity.RepoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepoDetailsViewModel : ViewModel() {

    private val repo: MutableStateFlow<RepoEntity?> = MutableStateFlow(null)

    fun repoFlow(): StateFlow<RepoEntity?> = repo

    fun getRepo(): RepoEntity? {
        return repo.value
    }

    fun setRepo(repo: RepoEntity) {
        this.repo.value = repo
    }
}