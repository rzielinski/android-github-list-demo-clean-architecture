package com.dreddi.android.githublist.presentation.views

interface OnRepoScrollListener {
    fun isLastPage(): Boolean
    fun isLoading(): Boolean
    fun loadMoreItems()
}
