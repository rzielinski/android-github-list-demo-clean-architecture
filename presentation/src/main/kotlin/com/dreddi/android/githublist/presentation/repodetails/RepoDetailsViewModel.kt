package com.dreddi.android.githublist.presentation.repodetails

import android.arch.lifecycle.MutableLiveData
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.app.BaseViewModel

class RepoDetailsViewModel: BaseViewModel() {

    var repo: MutableLiveData<RepoEntity> = MutableLiveData()
}