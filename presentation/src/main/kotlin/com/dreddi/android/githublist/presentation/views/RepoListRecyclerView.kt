package com.dreddi.android.githublist.presentation.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class RepoListRecyclerView @JvmOverloads constructor(
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

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                onRepoScrollListener?.let { listener ->
                    if (!listener.isLoading() && !listener.isLastPage() &&
                        !recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        listener.loadMoreItems()
                    }
                }
            }
        })
    }
}
