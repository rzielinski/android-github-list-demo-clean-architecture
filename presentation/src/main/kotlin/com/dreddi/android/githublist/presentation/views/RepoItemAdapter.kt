package com.dreddi.android.githublist.presentation.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.presentation.extension.formatCount
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

import java.util.ArrayList

class RepoItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading: Boolean = false
    private var repoItemsList: MutableList<RepoEntity> = mutableListOf()
    private var onRepoClickListener: OnRepoClickListener? = null

    fun setOnRepoClickListener(onRepoClickListener: OnRepoClickListener) {
        this.onRepoClickListener = onRepoClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == TYPE_ITEM) {
            val view = inflater.inflate(R.layout.view_repo_list_item, parent, false)
            RepoItemViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.view_repo_list_loading, parent, false)
            RepoItemLoadingViewHolder(view)
        }
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

        fun bind(repo: RepoEntity?) {
            repo?.run {
                itemView.repoListItemStars.text = stargazersCount.formatCount(itemView.resources)
                itemView.repoListItemName.text = name
                itemView.repoListItemDesc.text = description
                itemView.repoListItemLayout.setOnClickListener {
                    onRepoClickListener?.onRepoClicked(repo)
                }
            }
        }
    }

    private inner class RepoItemLoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {

        private const val TYPE_ITEM = 0
        private const val TYPE_LOADING = 1
    }
}