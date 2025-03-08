package com.example.splashscreenbaskit.controllers

import Product
import ProductResponse
import android.util.Log
import com.example.splashscreenbaskit.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductController {
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

    // Function to fetch products based on category
    fun getProducts(
        category: String,
        onResult: (List<Product>?, String?) -> Unit
    ) {
        val call = when (category) {
            "fruit" -> apiService.getFruits()
            "vegetable" -> apiService.getVegetables()
            "meat" -> apiService.getMeats()
            "fish" -> apiService.getFish()
            "frozen" -> apiService.getFrozenProducts()
            "spice" -> apiService.getSpices()
            else -> {
                onResult(null, "Invalid category")
                return
            }
        }

        // Enqueue the network request asynchronously
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    onResult(response.body(), null)
                } else {
                    onResult(null, "Failed to load products")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                onResult(null, "Network error: ${t.message}")
            }
        })
    }
}
