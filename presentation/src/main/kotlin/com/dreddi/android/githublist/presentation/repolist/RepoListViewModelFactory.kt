package com.dreddi.android.githublist.presentation.repolist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dreddi.android.githublist.domain.interactor.GetTopRepositories

class RepoListViewModelFactory(
        private var getTopRepositories: GetTopRepositories): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoListViewModel(getTopRepositories) as T
    }
}