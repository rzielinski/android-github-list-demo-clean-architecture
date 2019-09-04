package com.dreddi.android.githublist.presentation.views

import com.dreddi.android.githublist.domain.entity.RepoEntity

interface OnRepoClickListener {

    fun onRepoClicked(repo: RepoEntity)
}
