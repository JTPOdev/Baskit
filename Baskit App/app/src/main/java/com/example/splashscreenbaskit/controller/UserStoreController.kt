package com.example.splashscreenbaskit.controller

import StoreResponse
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.AccountDetails.uriToFilePart
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserStoreController(private val lifecycleOwner: LifecycleOwner, private val context: Context, private val apiService: ApiService) {

    init {
        TokenManager.init(context)
    }

    fun fetchStoreDetails(onResult: (Boolean, String?, StoreResponse?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
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
                        val store = storeList.first()
                        saveStoreLocally(store)
                        onResult(true, null, store)
                    } else {
                        Log.e("UserStoreController", "Store data is empty")
                        onResult(false, "No store details found", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UserStoreController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch store details", null)
                }
            } catch (e: HttpException) {
                Log.e("UserStoreController", "HTTP Error: ${e.message}")
                onResult(false, "Server error: ${e.message}", null)
            } catch (e: Exception) {
                Log.e("UserStoreController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveStoreLocally(store: StoreResponse) {
        context.getSharedPreferences("StorePrefs", Context.MODE_PRIVATE).edit().apply {
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

    fun uploadStoreImage(imageUri: Uri, onResult: (Boolean, String?) -> Unit) {
        val accessToken = TokenManager.getToken()

        if (accessToken.isNullOrEmpty()) {
            Log.e("UserStoreController", "No access token found")
            onResult(false, "Authentication failed. Please login again.")
            return
        }

        lifecycleOwner.lifecycleScope.launch {
            val filePart = uriToFilePart(context, "store_image", imageUri)

            if (filePart == null) {
                Log.e("UserStoreController", "File conversion failed")
                onResult(false, "Failed to convert image file")
                return@launch
            }

            try {
                val response = apiService.uploadStoreImage("Bearer $accessToken", filePart)

                if (response.isSuccessful) {
                    val imageUrl = response.body()?.imageUrl
                    Log.d("UserStoreController", "Image uploaded successfully: $imageUrl")
                    onResult(true, imageUrl)
                } else {
                    Log.e("UserStoreController", "Upload failed: ${response.errorBody()?.string()}")
                    onResult(false, "Upload failed. Try again.")
                }
            } catch (e: Exception) {
                Log.e("UserStoreController", "Error uploading image: ${e.localizedMessage}")
                onResult(false, "Error: ${e.localizedMessage}")
            }
        }
    }

    fun fetchMyStoreDetails(onResult: (Boolean, String?, StoreResponse?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getMyStoreDetails("Bearer $accessToken")

                if (response.isSuccessful) {
                    val storeResponse = response.body()
                    if (storeResponse != null) {
                        saveMyStoreLocally(storeResponse)
                        onResult(true, null, storeResponse)
                    } else {
                        Log.e("UserStoreController", "Store data is empty")
                        onResult(false, "No store details found", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UserStoreController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch store details", null)
                }
            } catch (e: HttpException) {
                Log.e("UserStoreController", "HTTP Error: ${e.message}")
                onResult(false, "Server error: ${e.message}", null)
            } catch (e: Exception) {
                Log.e("UserStoreController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }


    fun fetchStoreDetailsUserID(userId: Int, onResult: (Boolean, String?, StoreResponse?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getStoreDetailsUserID("Bearer $accessToken", userId)

                if (response.isSuccessful) {
                    val storeResponse = response.body()
                    if (storeResponse != null) {
                        saveMyStoreLocally(storeResponse)
                        onResult(true, null, storeResponse)
                    } else {
                        Log.e("UserStoreController", "Store data is empty")
                        onResult(false, "No store details found", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("UserStoreController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch store details", null)
                }
            } catch (e: HttpException) {
                Log.e("UserStoreController", "HTTP Error: ${e.message}")
                onResult(false, "Server error: ${e.message}", null)
            } catch (e: Exception) {
                Log.e("UserStoreController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveMyStoreLocally(store: StoreResponse) {
        context.getSharedPreferences("MyStorePrefs", Context.MODE_PRIVATE).edit().apply {
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
}
