package com.example.splashscreenbaskit.controller

import StoreRequestResponse
import com.example.splashscreenbaskit.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoreRequestController(
    private val apiService: ApiService
) {

    suspend fun sendStoreRequest(
        userId: String,
        storeName: String,
        ownerName: String,
        storePhone: String,
        storeAddress: String,
        storeOrigin: String,
        registeredStoreName: String,
        registeredStoreAddress: String,
        storeStatus: String,
        certificate: MultipartBody.Part?,
        validId: MultipartBody.Part?
    ): StoreRequestResponse {
        return try {
            val response = apiService.sendRequest(
                userId = userId.toRequestBody(),
                storeName = storeName.toRequestBody(),
                ownerName = ownerName.toRequestBody(),
                storePhone = storePhone.toRequestBody(),
                storeAddress = storeAddress.toRequestBody(),
                storeOrigin = storeOrigin.toRequestBody(),
                registeredStoreName = registeredStoreName.toRequestBody(),
                registeredStoreAddress = registeredStoreAddress.toRequestBody(),
                storeStatus = storeStatus.toRequestBody(),
                certificate = certificate,
                validId = validId
            )

            if (response.isSuccessful) {
                response.body() ?: StoreRequestResponse(success = false, message = "Unknown error", data = null)
            } else {
                StoreRequestResponse(success = false, message = "API request failed", data = null)
            }
        } catch (e: Exception) {
            StoreRequestResponse(success = false, message = e.localizedMessage ?: "Unknown error", data = null)
        }
    }
}

