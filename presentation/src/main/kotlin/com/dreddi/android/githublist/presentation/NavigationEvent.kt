package com.dreddi.android.githublist.presentation

import com.dreddi.android.githublist.domain.entity.RepoEntity

sealed class NavigationEvent {

    class ShowRepoDetails(val repo: RepoEntity) : NavigationEvent()
    class ShowExternalUrl(val url: String) : NavigationEvent()
}