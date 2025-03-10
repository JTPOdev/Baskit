package com.example.splashscreenbaskit.controllers

import Product
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ProductController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {
    private val apiService: ApiService = RetrofitInstance.create(ApiService::class.java)

    // Function to add a product
    fun addProduct(
        productName: String,
        productPrice: String,
        productCategory: String,
        productImage: MultipartBody.Part,
        token: String?,
        onSuccess: (ProductResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), productName)
        val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), productPrice)
        val categoryBody = RequestBody.create("text/plain".toMediaTypeOrNull(), productCategory)

        // Call the API method to add a product
        val response = apiService.addProduct(
            nameBody,
            priceBody,
            categoryBody,
            productImage,
            "Bearer $token"
        )

        // Enqueue the network request asynchronously
        response.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError("Failed to add product: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                onError("Network error: ${t.message}")
            }
        })
    }

    fun fetchProductDetails(onResult: (Boolean, String?, List<ProductsResponse>?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getProductDetails("Bearer $accessToken")

                if (response.isSuccessful) {
                    val productList = response.body()

                    if (productList != null && productList.isNotEmpty()) {
                        saveProductsLocally(productList)
                        onResult(true, null, productList)
                    } else {
                        Log.e("ProductController", "Product data is missing or empty in the response")
                        onResult(false, "Failed to fetch product details", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProductController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch product details", null)
                }
            } catch (e: HttpException) {
                Log.e("ProductController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("ProductController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }



    private fun saveProductsLocally(products: List<ProductsResponse>) {
        val sharedPreferences = context.getSharedPreferences("productPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val productsJson = Gson().toJson(products)
        editor.putString("products", productsJson)
        editor.apply()
    }

}
