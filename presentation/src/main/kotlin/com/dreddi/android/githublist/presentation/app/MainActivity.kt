package com.dreddi.android.githublist.presentation.app

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.presentation.repolist.RepoListFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    override fun getNavigatorFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun getNavigatorFragmentContainer(): Int {
        return R.id.activity_main_fragment
    }

    private fun setView() {
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.activity_main_toolbar)?.let {
            setSupportActionBar(it)
        }
        replaceFragmentExclusive(RepoListFragment.newInstance(), false)
    }
}
