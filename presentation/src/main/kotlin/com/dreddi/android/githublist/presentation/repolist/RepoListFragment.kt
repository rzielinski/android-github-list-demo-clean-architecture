package com.dreddi.android.githublist.presentation.repolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.app.Navigator
import com.dreddi.android.githublist.presentation.di.components.repolist.DaggerRepoListComponent
import com.dreddi.android.githublist.presentation.di.modules.repolist.RepoListModule
import com.dreddi.android.githublist.presentation.repodetails.RepoDetailsFragment
import com.dreddi.android.githublist.presentation.views.OnRepoClickListener
import com.dreddi.android.githublist.presentation.views.OnRepoScrollListener
import kotlinx.android.synthetic.main.fragment_repo_list.*
import javax.inject.Inject

class RepoListFragment : Fragment(), OnRepoClickListener, OnRepoScrollListener {

    @Inject
    lateinit var repoListViewModelFactory: RepoListViewModelFactory

    private var viewModel: RepoListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        observeViewState()
    }

    override fun onRepoClicked(repo: RepoEntity) {
        showRepoDetails(repo)
    }

    override fun isLastPage(): Boolean = false

    override fun isLoading(): Boolean = viewModel?.isLoading() == true

    override fun loadMoreItems() {
        viewModel?.fetchRepoList()
    }

    private fun injectDependency() {
        val repoListComponent = DaggerRepoListComponent.builder()
                .repoListModule(RepoListModule())
                .build()
        repoListComponent.inject(this)
        viewModel = ViewModelProviders.of(this, repoListViewModelFactory)
                .get(RepoListViewModel::class.java)
    }

    private fun setView() {
        with(repoListRecyclerView) {
            setOnRepoClickListener(this@RepoListFragment)
            setOnRepoScrollListener(this@RepoListFragment)
        }
    }

    private fun observeViewState() {
        viewModel?.getIsLoadingLiveData()?.observe(this, Observer {
            repoListRecyclerView?.setIsLoading(it ?: false)
        })
        viewModel?.getRepoListLiveData()?.observe(this, Observer {
            repoListRecyclerView?.addAll(it)
        })
        if (viewModel?.isData() == false) {
            viewModel?.fetchRepoList()
        }
    }

    private fun showRepoDetails(repo: RepoEntity) {
        if (activity is Navigator) {
            val navigator = activity as Navigator
            navigator.replaceFragment(
                    RepoDetailsFragment.newInstance(repo), true)
        }
    }

    companion object {

        fun newInstance(): RepoListFragment {
            return RepoListFragment()
        }
    }
}
