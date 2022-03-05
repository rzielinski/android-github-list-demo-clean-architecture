package com.dreddi.android.githublist.presentation.repodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.MainViewModel
import com.dreddi.android.githublist.presentation.NavigationEvent
import com.dreddi.android.githublist.presentation.common.formatCount
import com.dreddi.android.githublist.presentation.common.gone
import com.dreddi.android.githublist.presentation.common.loadFromUrl
import com.dreddi.android.githublist.presentation.common.show
import kotlinx.android.synthetic.main.fragment_repo_details.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RepoDetailsFragment : androidx.fragment.app.Fragment() {

    private val viewModel: RepoDetailsViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readArguments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun readArguments() {
        arguments?.getSerializable(ARG_REPO)?.let { repo ->
            viewModel.setRepo(repo as RepoEntity)
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.repoFlow().collect {
                updateView(it)
            }
        }
    }

    private fun updateView(repo: RepoEntity?) {
        if (repo != null) {
            repoDetailsName.text = repo.name
            repoDetailsDesc.text = repo.description
            repoDetailsWatch.text = repo.watchersCount.formatCount(resources)
            repoDetailsStars.text = repo.stargazersCount.formatCount(resources)
            repoDetailsFork.text = repo.forksCount.formatCount(resources)
            repoDetailsAvatar.loadFromUrl(repo.owner.avatarUrl, R.drawable.ic_person)
            repoDetailsSeeMore.setOnClickListener {
                showRepoHome()
            }
            repoDetailsLayout.show()
        } else {
            repoDetailsLayout.gone()
        }
    }

    private fun showRepoHome() {
        viewModel.getRepo()?.let { repo ->
            mainViewModel.setNavigationEvent(
                    NavigationEvent.ShowExternalUrl(repo.htmlUrl.orEmpty()))
        }
    }

    companion object {

        private const val ARG_REPO = "repo"

        fun getBundle(repo: RepoEntity? = null): Bundle {
            return Bundle().apply {
                putSerializable(ARG_REPO, repo)
            }
        }
    }
}
