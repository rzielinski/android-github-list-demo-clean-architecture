package com.dreddi.android.githublist.presentation.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dreddi.android.githublist.domain.usecase.GetTopRepositoriesUseCase

class RepoListViewModelFactory(
        private val getTopRepositoriesUseCase: GetTopRepositoriesUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoListViewModel(getTopRepositoriesUseCase) as T
    }
}