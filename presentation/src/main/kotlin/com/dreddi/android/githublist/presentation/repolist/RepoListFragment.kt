package com.dreddi.android.githublist.presentation.repolist

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.MainViewModel
import com.dreddi.android.githublist.presentation.NavigationEvent
import com.dreddi.android.githublist.presentation.views.OnRepoScrollListener
import com.dreddi.android.githublist.presentation.repolist.adapter.RepoItemAdapter
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RepoListFragment : androidx.fragment.app.Fragment(), OnRepoScrollListener {

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

    private lateinit var repoItemAdapter: RepoItemAdapter
    private val viewModel: RepoListViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        observe()
    }

    override fun isLastPage(): Boolean = false

    override fun isLoading(): Boolean = viewModel.isLoading()

    override fun loadMoreItems() {
        viewModel.fetchRepoList()
    }

    private fun setView() {
        with(repoListRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = RepoItemAdapter(::onRepoClick).also {
                repoItemAdapter = it
            }
        }
    }

    private fun observe() {
        viewModel.getIsLoadingLiveData().observe(this, Observer {
            repoItemAdapter.setIsLoading(it == true)
        })
        viewModel.getRepoListLiveData().observe(this, Observer {
            repoItemAdapter.append(it)
        })
        if (!viewModel.isData()) {
            viewModel.fetchRepoList()
        }
    }

    private fun onRepoClick(repo: RepoEntity) {
        mainViewModel.setNavigationEvent(
                NavigationEvent.ShowRepoDetails(repo))
    }
}
