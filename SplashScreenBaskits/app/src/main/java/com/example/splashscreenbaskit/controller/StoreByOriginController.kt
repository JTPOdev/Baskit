package com.example.splashscreenbaskit.controllers

import StoreResponse
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StoreByOriginController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {

    init {
        TokenManager.init(context)
    }

    fun fetchStoresByOrigin(
        origin: String,
        onResult: (Boolean, String?, List<StoreResponse>?) -> Unit
    ) {
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

                    if (!storeList.isNullOrEmpty()) {
                        val filteredStores = storeList.filter { it.store_origin == origin }
                        saveStoresLocally(filteredStores, origin)
                        onResult(true, null, filteredStores)
                    } else {
                        Log.e("StoreByOriginController", "No stores found")
                        onResult(false, "No stores available", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("StoreByOriginController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch stores", null)
                }
            } catch (e: HttpException) {
                Log.e("StoreByOriginController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("StoreByOriginController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveStoresLocally(stores: List<StoreResponse>, origin: String) {
        val sharedPreferences = context.getSharedPreferences("${origin}_StorePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("stores", Gson().toJson(stores))
            apply()
        }
    }
}
