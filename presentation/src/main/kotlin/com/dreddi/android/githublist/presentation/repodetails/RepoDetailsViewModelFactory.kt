package com.dreddi.android.githublist.presentation.repodetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class RepoDetailsViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoDetailsViewModel() as T
    }
}