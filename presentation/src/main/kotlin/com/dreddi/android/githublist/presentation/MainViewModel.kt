package com.dreddi.android.githublist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dreddi.android.githublist.presentation.common.Event

class MainViewModel : ViewModel() {

    private val navigationEvent = MutableLiveData<Event<NavigationEvent>>()

    fun getNavigationEventLiveData() = navigationEvent as LiveData<Event<NavigationEvent>>

    fun setNavigationEvent(event: NavigationEvent) {
        navigationEvent.value = Event(event)
    }
}