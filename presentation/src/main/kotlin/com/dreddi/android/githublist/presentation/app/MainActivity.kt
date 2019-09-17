package com.dreddi.android.githublist.presentation.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.dreddi.android.githublist.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
    }

    private fun setView() {
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.activity_main_toolbar)?.let {
            setSupportActionBar(it)
        }
    }
}
