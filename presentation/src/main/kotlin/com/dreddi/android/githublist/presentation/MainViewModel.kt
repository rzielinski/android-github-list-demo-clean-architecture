package com.dreddi.android.githublist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreddi.android.githublist.presentation.common.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val navigationEvent = MutableSharedFlow<Event<NavigationEvent>>()

    fun navigationEventFlow(): SharedFlow<Event<NavigationEvent>> = navigationEvent

    fun setNavigationEvent(event: NavigationEvent) {
        viewModelScope.launch {
            navigationEvent.emit(Event(event))
        }
    }
}
