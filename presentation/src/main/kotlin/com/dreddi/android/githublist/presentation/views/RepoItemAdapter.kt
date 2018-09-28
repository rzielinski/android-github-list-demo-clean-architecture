package com.dreddi.android.githublist.presentation.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity

import java.util.ArrayList

class RepoItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading: Boolean = false
    private var repoItemsList: MutableList<RepoEntity>
    private var onRepoClickListener: OnRepoClickListener? = null

    init {
        repoItemsList = ArrayList()
    }

    fun setOnRepoClickListener(onRepoClickListener: OnRepoClickListener) {
        this.onRepoClickListener = onRepoClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == TYPE_ITEM) {
            val view = inflater.inflate(R.layout.view_repo_list_item, parent, false)
            viewHolder = RepoItemViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.view_repo_list_loading, parent, false)
            viewHolder = RepoItemLoadingViewHolder(view)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            (holder as RepoItemViewHolder).bind(repoItemsList[position])
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) repoItemsList.size + 1 else repoItemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == repoItemsList.size) {
            TYPE_LOADING
        } else TYPE_ITEM
    }

    fun add(repo: RepoEntity?) {
        if ((repo == null) || repoItemsList.contains(repo)) {
            return
        }
        repoItemsList.add(repo)
        notifyItemInserted(repoItemsList.size - 1)
    }

    fun addAll(repoItemsList: List<RepoEntity>?) {
        if (repoItemsList == null) {
            return
        }
        for (repo in repoItemsList) {
            add(repo)
        }
    }

    fun set(repoItemsList: List<RepoEntity>?) {
        this.repoItemsList = ArrayList()
        addAll(repoItemsList)
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    private inner class RepoItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val repoName: TextView
        private val repoDescr: TextView
        private val repoStars: TextView
        private val layout: LinearLayout

        init {
            repoName = view.findViewById(R.id.view_repo_list_item_name)
            repoDescr = view.findViewById(R.id.view_repo_list_item_descr)
            repoStars = view.findViewById(R.id.view_repo_list_item_stars)
            layout = view.findViewById(R.id.view_repo_list_item_layout)
        }

        fun bind(repo: RepoEntity?) {

            if (repo == null) {
                return
            }

            repoName.text = repo.name
            repoDescr.text = repo.description
            repoStars.text = formatCount(repo.stargazersCount ?: 0)

            layout.setOnClickListener {
                if (onRepoClickListener != null) {
                    onRepoClickListener!!.onRepoClicked(repo)
                }
            }
        }

        fun formatCount(count: Long?): String {
            if (count == null) {
                return ""
            }
            if (count >= 1000) {
                val countK = (count / 1000).toInt()
                return itemView.context?.getString(R.string.format_count_k, countK) ?: ""
            } else {
                return count.toString()
            }
        }
    }

    private inner class RepoItemLoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {

        private val TYPE_ITEM = 0
        private val TYPE_LOADING = 1
    }
}