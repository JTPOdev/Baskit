package com.example.splashscreenbaskit.controllers


import Product
import ProductOriginResponse
import ProductResponse
import ProductsResponse
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProductByOriginController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {

    fun fetchProductsByOrigin(
        category: String,
        origin: String,
        onResult: (Boolean, String?, List<ProductsResponse>?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getProductsByOrigin("Bearer $accessToken", origin)

                if (response.isSuccessful) {
                    val productList = response.body()

                    if (!productList.isNullOrEmpty()) {
                        val filteredList = productList.filter { it.product_category == category }
                        saveProductsLocally(filteredList, origin)
                        onResult(true, null, filteredList)
                    } else {
                        onResult(false, "No products found for this location", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProductByOriginController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch products", null)
                }
            } catch (e: HttpException) {
                Log.e("ProductByOriginController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("ProductByOriginController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveProductsLocally(products: List<ProductsResponse>, origin: String) {
        val sharedPreferences = context.getSharedPreferences("${origin}_ProductPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val productsJson = Gson().toJson(products)
        editor.putString("products", productsJson)
        editor.apply()
    }
}
