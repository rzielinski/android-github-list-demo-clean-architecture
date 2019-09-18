package com.dreddi.android.githublist.presentation.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class RepoListRecyclerView(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
): RecyclerView(context, attrs, defStyle) {

    private lateinit var layoutManager: LinearLayoutManager
    private var onRepoScrollListener: OnRepoScrollListener? = null

    init {
        setView(context)
    }

    fun setOnRepoScrollListener(onRepoScrollListener: OnRepoScrollListener) {
        this.onRepoScrollListener = onRepoScrollListener
    }

    private fun setView(context: Context) {

        layoutManager = LinearLayoutManager(context).also {
            setLayoutManager(it)
        }

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
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
