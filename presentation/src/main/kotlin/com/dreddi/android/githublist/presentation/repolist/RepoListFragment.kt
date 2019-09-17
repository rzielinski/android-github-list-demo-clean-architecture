package com.dreddi.android.githublist.presentation.repolist

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.repodetails.RepoDetailsFragment
import com.dreddi.android.githublist.presentation.views.OnRepoClickListener
import com.dreddi.android.githublist.presentation.views.OnRepoScrollListener
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class RepoListFragment : androidx.fragment.app.Fragment(), OnRepoClickListener, OnRepoScrollListener {

    /** Dagger 2
    @Inject
    lateinit var repoListViewModelFactory: RepoListViewModelFactory

    private fun injectDependency() {
        val repoListComponent = DaggerRepoListComponent.builder()
                .repoListModule(RepoListModule())
                .build()
        repoListComponent.inject(this)
        viewModel = ViewModelProviders.of(this, repoListViewModelFactory)
                .get(RepoListViewModel::class.java)
    }**/

    private val viewModel: RepoListViewModel by viewModel()

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

    override fun isLoading(): Boolean = viewModel.isLoading()

    override fun loadMoreItems() {
        viewModel.fetchRepoList()
    }

    private fun setView() {
        with(repoListRecyclerView) {
            setOnRepoClickListener(this@RepoListFragment)
            setOnRepoScrollListener(this@RepoListFragment)
        }
    }

    private fun observeViewState() {
        viewModel.getIsLoadingLiveData().observe(this, Observer {
            repoListRecyclerView?.setIsLoading(it ?: false)
        })
        viewModel.getRepoListLiveData().observe(this, Observer {
            repoListRecyclerView?.addAll(it)
        })
        if (!viewModel.isData()) {
            viewModel.fetchRepoList()
        }
    }

    private fun showRepoDetails(repo: RepoEntity) {
        val bundle = RepoDetailsFragment.getBundle(repo)
        view?.findNavController()
                ?.navigate(R.id.action_repoListFragment_to_repoDetailsFragment, bundle)
    }
}
