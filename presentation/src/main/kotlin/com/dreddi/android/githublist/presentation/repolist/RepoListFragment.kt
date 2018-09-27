package com.dreddi.android.githublist.presentation.repolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.data.network.GitHubApi
import com.dreddi.android.githublist.data.mapper.RepoDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoListDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoOwnerDataToEntityMapper
import com.dreddi.android.githublist.data.repository.RepoRepositoryImpl
import com.dreddi.android.githublist.data.repository.store.CloudRepoStore
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.domain.interactor.GetTopRepositories
import com.dreddi.android.githublist.presentation.di.ApiClient
import com.dreddi.android.githublist.presentation.views.OnRepoClickListener
import com.dreddi.android.githublist.presentation.views.OnRepoScrollListener
import com.dreddi.android.githublist.presentation.views.RepoListRecyclerView

class RepoListFragment: Fragment(), OnRepoClickListener, OnRepoScrollListener {

    private var viewModel: RepoListViewModel? = null
    private var repoListRecyclerView: RepoListRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        if (viewModel?.isData() == false) {
            viewModel?.fetchRepoList()
        }
    }

    override fun onRepoClicked(repo: RepoEntity) {
    }

    override fun isLastPage(): Boolean {
        return false
    }

    override fun isLoading(): Boolean {
        return false
    }

    override fun loadMoreItems() {
    }

    private fun injectDependency() {
        val retrofit = ApiClient.getClient()
        var api = retrofit.create(GitHubApi::class.java);
        var repoStore = CloudRepoStore(api)
        var factory = RepoListViewModelFactory(
                GetTopRepositories(
                        RepoRepositoryImpl(repoStore,
                                RepoListDataToEntityMapper(RepoDataToEntityMapper(RepoOwnerDataToEntityMapper())))
                ))
        viewModel = ViewModelProviders.of(this, factory)
                .get(RepoListViewModel::class.java)
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_repo_list, container, false)
        repoListRecyclerView = view.findViewById<RepoListRecyclerView>(R.id.fragment_repo_list)
        repoListRecyclerView?.setOnRepoClickListener(this)
        repoListRecyclerView?.setOnRepoScrollListener(this)
        return view
    }

    private fun observeViewState() {
        viewModel?.isLoading?.observe(this, Observer {
            repoListRecyclerView?.setIsLoading(it ?: false)
        })
        viewModel?.repoList?.observe(this, Observer {
            repoListRecyclerView?.addItems(it?.repoDataItemsList)
        })
    }

    companion object {
        fun newInstance(): RepoListFragment {
            return RepoListFragment()
        }
    }
}
