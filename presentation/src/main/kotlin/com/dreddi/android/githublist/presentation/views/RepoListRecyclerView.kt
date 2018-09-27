package com.dreddi.android.githublist.presentation.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

import com.dreddi.android.githublist.domain.entity.RepoEntity

class RepoListRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    private var adapter: RepoItemAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var onRepoScrollListener: OnRepoScrollListener? = null

    val items: List<RepoEntity>
        get() = adapter!!.all

    init {
        setView(context)
    }

    fun addItems(repoItemsList: List<RepoEntity>?) {
        adapter!!.addAll(repoItemsList)
    }

    fun setIsLoading(isLoading: Boolean) {
        adapter!!.setIsLoading(isLoading)
    }

    fun setOnRepoClickListener(onRepoClickListener: OnRepoClickListener) {
        adapter!!.setOnRepoClickListener(onRepoClickListener)
    }

    fun setOnRepoScrollListener(onRepoScrollListener: OnRepoScrollListener) {
        this.onRepoScrollListener = onRepoScrollListener
    }

    private fun setView(context: Context) {

        layoutManager = LinearLayoutManager(context)
        setLayoutManager(layoutManager)

        adapter = RepoItemAdapter()
        setAdapter(adapter)

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (onRepoScrollListener == null) {
                    return
                }

                val visibleItemCount = layoutManager!!.childCount
                val totalItemCount = layoutManager!!.itemCount
                val firstVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()

                if (!onRepoScrollListener!!.isLoading() && !onRepoScrollListener!!.isLastPage()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        onRepoScrollListener!!.loadMoreItems()
                    }
                }
            }
        })
    }
}
