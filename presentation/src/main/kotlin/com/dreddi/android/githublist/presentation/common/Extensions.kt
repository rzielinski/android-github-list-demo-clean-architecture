package com.dreddi.android.githublist.presentation.common

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dreddi.android.githublist.R

fun Long?.formatCount(res: Resources): String {
    return this?.let { count ->
        if (count >= 1000) {
            val countK = (count / 1000).toInt()
            res.getString(R.string.format_count_k, countK)
        } else {
            count.toString()
        }
    } ?: ""
}

fun ImageView.loadFromUrl(url: String?, errorDrawableResId: Int) {
    Glide.with(this)
            .applyDefaultRequestOptions(
                    RequestOptions().error(errorDrawableResId))
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
