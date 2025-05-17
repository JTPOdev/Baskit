package com.example.splashscreenbaskit.controller

import User
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {

    init {
        TokenManager.init(context)
    }

    fun fetchUserDetails(onResult: (Boolean, String?, User?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getUserDetails("Bearer $accessToken")

                if (response.isSuccessful) {
                    val users = response.body()?.user

                    if (users != null) {
                        val user = User(
                            name = "${users.firstname ?: ""} ${users.lastname ?: ""}",
                            username = users.username ?: "Unknown",
                            email = users.email ?: "Unknown",
                            contactNumber = users.mobile_number ?: "Unknown",
                            role = users.role ?: "Unknown"
                        )

                        saveUserLocally(user)
                        onResult(true, null, user)
                    } else {
                        Log.e("UserController", "User data is missing in the response")
                        onResult(false, "Failed to fetch user details", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UserController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch user details", null)
                }
            } catch (e: HttpException) {
                Log.e("UserController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("UserController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveUserLocally(user: User) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("name", user.name)
            putString("username", user.username)
            putString("email", user.email)
            putString("contactNumber", user.contactNumber)
            putString("role", user.role)
            apply()
        }
    }
}
