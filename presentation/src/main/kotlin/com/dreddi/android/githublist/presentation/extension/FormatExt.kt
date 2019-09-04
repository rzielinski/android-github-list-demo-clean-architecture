package com.dreddi.android.githublist.presentation.extension

import android.content.res.Resources
import android.view.View
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

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}