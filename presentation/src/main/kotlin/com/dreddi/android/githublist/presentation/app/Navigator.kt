package com.dreddi.android.githublist.presentation.app

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

interface Navigator {

    fun getNavigatorFragmentManager(): FragmentManager
    fun getNavigatorFragmentContainer(): Int

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        var fm = getNavigatorFragmentManager()
        var container = getNavigatorFragmentContainer()
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

    fun replaceFragmentOnce(fragment: Fragment, addToBackStack: Boolean) {
        var fm = getNavigatorFragmentManager()
        if (fm.findFragmentByTag(fragment.javaClass.name) == null) {
            replaceFragment(fragment, addToBackStack)
        }
    }
}