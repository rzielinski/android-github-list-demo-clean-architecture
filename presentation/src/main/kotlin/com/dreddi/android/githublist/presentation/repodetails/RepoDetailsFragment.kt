package com.dreddi.android.githublist.presentation.repodetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.di.components.repodetails.DaggerRepoDetailsComponent
import com.dreddi.android.githublist.presentation.di.modules.repodetails.RepoDetailsModule
import javax.inject.Inject

class RepoDetailsFragment : Fragment() {

    @Inject
    lateinit var repoDetailsViewModelFactory: RepoDetailsViewModelFactory

    private var viewModel: RepoDetailsViewModel? = null

    private var repoName: TextView? = null
    private var repoDescr: TextView? = null
    private var repoWatch: TextView? = null
    private var repoStars: TextView? = null
    private var repoFork: TextView? = null
    private var seeMore: TextView? = null
    private var avatar: ImageView? = null
    private var layoutDetails: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        readArguments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
    }

    private fun readArguments() {
        if (arguments != null && arguments!!.containsKey(ARG_REPO)) {
            var repo = arguments!!.getSerializable(ARG_REPO) as RepoEntity
            viewModel?.repo?.value = repo
        }
    }

    private fun injectDependency() {
        var repoDetailsComponent =  DaggerRepoDetailsComponent.builder()
                .repoDetailsModule(RepoDetailsModule())
                .build()
        repoDetailsComponent.inject(this)
        viewModel = ViewModelProviders.of(this, repoDetailsViewModelFactory)
                .get(RepoDetailsViewModel::class.java)
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_repo_details, container, false)

        avatar = view.findViewById(R.id.fragment_repo_details_avatar)
        repoName = view.findViewById(R.id.fragment_repo_details_name)
        repoDescr = view.findViewById(R.id.fragment_repo_details_descr)
        repoWatch = view.findViewById(R.id.fragment_repo_details_watch)
        repoStars = view.findViewById(R.id.fragment_repo_details_stars)
        repoFork = view.findViewById(R.id.fragment_repo_details_fork)
        layoutDetails = view.findViewById(R.id.fragment_repo_details_layout)

        seeMore = view.findViewById(R.id.fragment_repo_details_see_more)
        seeMore?.setOnClickListener { showRepoHome() }

        return view
    }

    private fun observeViewState() {
        viewModel?.repo?.observe(this, Observer {
            updateView(it)
        })
    }

    private fun updateView(repo: RepoEntity?) {

        if (repo == null) {
            layoutDetails!!.visibility = View.GONE
            return
        }

        repoName?.text = repo.name
        repoDescr?.text = repo.description

        repoWatch?.text = formatCount(repo.watchersCount)
        repoStars?.text = formatCount(repo.stargazersCount)
        repoFork?.text = formatCount(repo.forksCount)

        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions()
                        .error(R.drawable.ic_person))
                .load(repo.owner.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(avatar!!)

        layoutDetails?.visibility = View.VISIBLE
    }

    private fun showRepoHome() {
        if (viewModel?.repo?.value == null) {
            return
        }
        startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse(viewModel?.repo?.value!!.htmlUrl)))
    }

    fun formatCount(count: Long?): String {
        if (count == null) {
            return ""
        }
        if (count >= 1000) {
            val countK = (count / 1000).toInt()
            return activity?.getString(R.string.format_count_k, countK) ?: ""
        } else {
            return count.toString()
        }
    }

    companion object {

        private val ARG_REPO = "repo"

        fun newInstance(repo: RepoEntity? = null): RepoDetailsFragment {
            val fragment = RepoDetailsFragment()
            val params = Bundle()
            if (repo != null) {
                params.putSerializable(ARG_REPO, repo)
            }
            fragment.arguments = params
            return fragment
        }
    }
}
