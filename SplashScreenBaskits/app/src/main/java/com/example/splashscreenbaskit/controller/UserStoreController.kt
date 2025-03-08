package com.example.splashscreenbaskit.controller

import StoreResponse
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserStoreController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {

    init {
        TokenManager.init(context)
    }

    fun fetchStoreDetails(onResult: (Boolean, String?, StoreResponse?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getStoreDetails("Bearer $accessToken")

                if (response.isSuccessful) {
                    val storeList = response.body()

                    if (storeList != null && storeList.isNotEmpty()) {
                        val store = storeList.first()
                        saveStoreLocally(store)
                        onResult(true, null, store)
                    } else {
                        Log.e("UserStoreController", "Store data is missing or empty in the response")
                        onResult(false, "Failed to fetch store details", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UserStoreController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch store details", null)
                }
            } catch (e: HttpException) {
                Log.e("UserStoreController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("UserStoreController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveStoreLocally(store: StoreResponse) {
        val sharedPreferences = context.getSharedPreferences("StorePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("store_name", store.store_name)
            putString("store_address", store.store_address)
            putString("store_phone_number", store.store_phone_number)
            putString("store_origin", store.store_origin)
            putString("store_status", store.store_status)
            putString("store_rating", store.store_rating)
            putString("store_image", store.store_image ?: "")
            apply()
        }
    }

    fun getStore(): StoreResponse {
        val sharedPreferences = context.getSharedPreferences("StorePrefs", Context.MODE_PRIVATE)
        return StoreResponse(
            id = "",
            user_id = "",
            store_name = sharedPreferences.getString("store_name", "Unknown") ?: "Unknown",
            owner_name = "",
            store_phone_number = sharedPreferences.getString("store_phone_number", "Unknown") ?: "Unknown",
            store_address = sharedPreferences.getString("store_address", "Unknown") ?: "Unknown",
            store_origin = sharedPreferences.getString("store_origin", "Unknown") ?: "Unknown",
            store_rating = sharedPreferences.getString("store_rating", "0") ?: "0",
            store_status = sharedPreferences.getString("store_status", "Unknown") ?: "Unknown",
            registered_store_name = "",
            registered_store_address = "",
            certificate_of_registration = "",
            valid_id = "",
            store_image = sharedPreferences.getString("store_image", ""),
            created_at = ""
        )
    }
}
