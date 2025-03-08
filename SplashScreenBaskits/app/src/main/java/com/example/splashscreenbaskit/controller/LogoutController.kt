package com.example.splashscreenbaskit.controller

import LogoutRequest
import RetrofitInstance
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LogoutController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {

    init {
        TokenManager.init(context)
    }

    fun logout(onResult: (Boolean, String) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val token = TokenManager.getToken()

            if (token.isNullOrEmpty()) {
                onResult(false, "No token found. User might already be logged out.")
                return@launch
            }
            Log.d("LogoutController", "Using token: $token")
            try {
                val response = apiService.logout("Bearer $token")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("LogoutController", "Logout response body: $responseBody")

                    TokenManager.clearToken()
                    onResult(true, "Logout successful!")
                    Log.d("LogoutController", "Logout successful. Token cleared.")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LogoutController", "Logout failed. Error body: $errorBody")
                    onResult(false, "Logout failed: ${response.message()}")
                }
            } catch (e: HttpException) {
                Log.e("LogoutController", "HttpException: ${e.message}")
                onResult(false, "Logout error: ${e.message()}")
            } catch (e: Exception) {
                Log.e("LogoutController", "Unexpected error: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}