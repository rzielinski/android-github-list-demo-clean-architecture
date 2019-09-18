package com.dreddi.android.githublist.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import org.koin.android.viewmodel.ext.android.viewModel

import com.dreddi.android.githublist.R
import com.dreddi.android.githublist.presentation.repodetails.RepoDetailsFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        observe()
    }

    private fun setView() {
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.activity_main_toolbar)?.let {
            setSupportActionBar(it)
        }
    }

    private fun observe() {
        viewModel.getNavigationEventLiveData().observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is NavigationEvent.ShowRepoDetails -> it.navigate()
                    is NavigationEvent.ShowExternalUrl -> it.navigate()
                }
            }
        })
    }

    private fun NavigationEvent.ShowRepoDetails.navigate() {
        RepoDetailsFragment.getBundle(repo).also { bundle ->
            findNavController(R.id.activity_host_fragment).navigate(
                    R.id.action_repoListFragment_to_repoDetailsFragment, bundle)
        }
    }

    private fun NavigationEvent.ShowExternalUrl.navigate() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
