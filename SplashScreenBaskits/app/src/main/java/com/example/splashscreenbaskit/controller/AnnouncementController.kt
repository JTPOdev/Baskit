package com.example.splashscreenbaskit.controller

import Announcement
import com.example.splashscreenbaskit.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnnouncementController {
    val apiService = RetrofitInstance.create(ApiService::class.java)

    suspend fun fetchAnnouncementImages(): Announcement? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getSlideImages()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}