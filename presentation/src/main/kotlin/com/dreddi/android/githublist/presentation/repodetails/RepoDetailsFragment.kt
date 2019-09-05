package com.dreddi.android.githublist.presentation.repodetails

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.extension.formatCount
import com.dreddi.android.githublist.presentation.extension.gone
import com.dreddi.android.githublist.presentation.extension.show
import kotlinx.android.synthetic.main.fragment_repo_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class RepoDetailsFragment : Fragment() {

    /** Dagger2
    @Inject
    lateinit var repoDetailsViewModelFactory: RepoDetailsViewModelFactory

    private fun injectDependency() {
        val repoDetailsComponent = DaggerRepoDetailsComponent.builder()
                .repoDetailsModule(RepoDetailsModule())
                .build()
        repoDetailsComponent.inject(this)
        viewModel = ViewModelProviders.of(this, repoDetailsViewModelFactory)
                .get(RepoDetailsViewModel::class.java)
    }**/

    private val viewModel: RepoDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readArguments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
    }

    private fun readArguments() {
        arguments?.takeIf { it.containsKey(ARG_REPO) }?.let { arguments ->
            viewModel.setRepo(arguments.getSerializable(ARG_REPO) as RepoEntity)
        }
    }

    private fun observeViewState() {
        viewModel.getRepoLiveData().observe(this, Observer {
            updateView(it)
        })
    }

    private fun updateView(repo: RepoEntity?) {

        if (repo != null) {

            Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions()
                            .error(R.drawable.ic_person))
                    .load(repo.owner.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(repoDetailsAvatar)

            repoDetailsName.text = repo.name
            repoDetailsDesc.text = repo.description
            repoDetailsWatch.text = repo.watchersCount.formatCount(resources)
            repoDetailsStars.text = repo.stargazersCount.formatCount(resources)
            repoDetailsFork.text = repo.forksCount.formatCount(resources)

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
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(repo.htmlUrl)))
        }
    }

    companion object {

        private const val ARG_REPO = "repo"

        fun newInstance(repo: RepoEntity? = null): RepoDetailsFragment {
            return RepoDetailsFragment().apply {
                repo?.let {
                    arguments = Bundle().apply {
                        putSerializable(ARG_REPO, repo)
                    }
                }
            }
        }
    }
}
