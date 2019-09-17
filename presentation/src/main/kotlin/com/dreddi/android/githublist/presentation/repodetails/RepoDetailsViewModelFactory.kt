package com.dreddi.android.githublist.presentation.repodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepoDetailsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoDetailsViewModel() as T
    }
}