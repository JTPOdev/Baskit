package com.example.splashscreenbaskit.Home

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashscreenbaskit.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var dagupanVendors by mutableStateOf<List<Vendor>>(emptyList())
        private set
    var calasiaoVendors by mutableStateOf<List<Vendor>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set


}