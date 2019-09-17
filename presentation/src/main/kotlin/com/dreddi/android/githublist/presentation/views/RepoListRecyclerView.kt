package com.dreddi.android.githublist.presentation.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet

import com.dreddi.android.githublist.domain.entity.RepoEntity

class RepoListRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): androidx.recyclerview.widget.RecyclerView(context, attrs, defStyle) {

    private lateinit var adapter: RepoItemAdapter
    private lateinit var layoutManager: androidx.recyclerview.widget.LinearLayoutManager
    private var onRepoScrollListener: OnRepoScrollListener? = null

    init {
        setView(context)
    }

    fun addAll(repoItemsList: List<RepoEntity>?) {
        adapter.addAll(repoItemsList)
    }

    fun set(repoItemsList: List<RepoEntity>?) {
        adapter.set(repoItemsList)
    }

    fun setIsLoading(isLoading: Boolean) {
        adapter.setIsLoading(isLoading)
    }

    fun setOnRepoClickListener(onRepoClickListener: OnRepoClickListener) {
        adapter.setOnRepoClickListener(onRepoClickListener)
    }

    fun setOnRepoScrollListener(onRepoScrollListener: OnRepoScrollListener) {
        this.onRepoScrollListener = onRepoScrollListener
    }

    private fun setView(context: Context) {

        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context).also {
            setLayoutManager(it)
        }

        adapter = RepoItemAdapter().also {
            setAdapter(it)
        }

        addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                onRepoScrollListener?.let { listener ->

                    if (!listener.isLoading() && !listener.isLastPage()) {

                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            listener.loadMoreItems()
                        }
                    }
                }
            }
        })
    }
}
