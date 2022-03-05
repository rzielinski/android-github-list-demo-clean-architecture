package com.dreddi.android.githublist.presentation.repolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.MainViewModel
import com.dreddi.android.githublist.presentation.NavigationEvent
import com.dreddi.android.githublist.presentation.views.OnRepoScrollListener
import com.dreddi.android.githublist.presentation.repolist.adapter.RepoItemAdapter
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RepoListFragment : androidx.fragment.app.Fragment(), OnRepoScrollListener {

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
            setOnRepoScrollListener(this@RepoListFragment)
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.isLoadingFlow().collect {
                repoItemAdapter.setIsLoading(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.repoListFlow().collect {
                repoItemAdapter.append(it)
            }
        }
    }

    private fun onRepoClick(repo: RepoEntity) {
        mainViewModel.setNavigationEvent(
                NavigationEvent.ShowRepoDetails(repo))
    }
}
