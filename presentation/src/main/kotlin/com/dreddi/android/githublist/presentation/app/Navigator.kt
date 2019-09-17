package com.dreddi.android.githublist.presentation.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Navigator {

    fun getNavigatorFragmentManager(): androidx.fragment.app.FragmentManager
    fun getNavigatorFragmentContainer(): Int

    fun replaceFragment(fragment: androidx.fragment.app.Fragment, addToBackStack: Boolean) {
        val fm = getNavigatorFragmentManager()
        val container = getNavigatorFragmentContainer()
        if (addToBackStack) {
            fm.beginTransaction()
                    .replace(container, fragment, fragment.javaClass.name)
                    .addToBackStack(null)
                    .commit()
        } else {
            fm.beginTransaction()
                    .replace(container, fragment, fragment.javaClass.name)
                    .commit()
        }
    }

    fun replaceFragmentExclusive(fragment: androidx.fragment.app.Fragment, addToBackStack: Boolean) {
        if (getNavigatorFragmentManager().findFragmentByTag(fragment.javaClass.name) == null) {
            replaceFragment(fragment, addToBackStack)
        }
    }
}