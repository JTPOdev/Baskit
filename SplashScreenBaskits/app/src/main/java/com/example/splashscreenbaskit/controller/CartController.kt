package com.example.splashscreenbaskit.controller

import CartItem
import CartResponse
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CartController(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val apiService: ApiService
) {

    init {
        TokenManager.init(context)
    }

    fun addToCart(
        productId: Int,
        productName: String,
        productPrice: Double,
        productOrigin: String,
        storeId: Int,
        storeName: String,
        productQuantity: Int,
        productPortion: String,
        productImageUrl: String,
        orderStatus: String,
        status: String,
        tagabiliFirstname: String,
        tagabiliLastname: String,
        tagabiliMobile: String,
        tagabiliEmail: String,
        orderCode: String?,
        callback: (CartResponse) -> Unit
    ) {
        val cartItem = CartItem(
            product_id = productId,
            product_name = productName,
            product_price = productPrice,
            product_origin = productOrigin,
            store_id = storeId,
            store_name = storeName,
            product_quantity = productQuantity,
            product_portion = productPortion,
            product_image = productImageUrl,
            order_status = orderStatus,
            status = status,
            tagabili_firstname = tagabiliFirstname,
            tagabili_lastname = tagabiliLastname,
            tagabili_mobile = tagabiliMobile,
            tagabili_email = tagabiliEmail,
            order_code = orderCode
        )

        apiService.addToCart(cartItem).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if (response.isSuccessful) {
                    callback(response.body() ?: CartResponse(false, "Unknown error"))
                } else {
                    callback(CartResponse(false, "Failed to add product to cart"))
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                callback(CartResponse(false, t.localizedMessage ?: "Unknown error"))
            }
        })
    }


    fun fetchCartItems(onResult: (Boolean, String?, List<CartItem>?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getCartItems("Bearer $accessToken")

                if (response.isSuccessful) {
                    val cartList = response.body()
                    onResult(true, null, cartList ?: emptyList())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CartController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch cart items", null)
                }
            } catch (e: HttpException) {
                Log.e("CartController", "HTTP Error: ${e.message}")
                onResult(false, "Server error: ${e.message}", null)
            } catch (e: Exception) {
                Log.e("CartController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveCartLocally(cartList: List<CartItem>) {
        val prefs = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE).edit()
        val json = Gson().toJson(cartList)
        prefs.putString("cart_data", json)
        prefs.apply()
    }


    fun updateCart(
        productId: Int,
        newQuantity: Int,
        portion: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val accessToken = TokenManager.getToken()
            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found")
                return@launch
            }

            // Convert data to JSON request body
            val jsonObject = JSONObject().apply {
                put("product_id", productId)
                put("product_quantity", newQuantity)
                put("product_portion", portion)
            }
            val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

            try {
                val response = apiService.updateCart("Bearer $accessToken", requestBody)
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Failed to update cart"
                    Log.e("CartController", "API Error: $errorMessage")
                    onResult(false, errorMessage)
                }
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    fun removeFromCart(
        productId: Int,
        portion: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val accessToken = TokenManager.getToken()
            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found")
                return@launch
            }

            // Convert data to JSON request body
            val jsonObject = JSONObject().apply {
                put("product_id", productId)
                put("product_portion", portion)
            }
            val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
            Log.d("CartController", "Sending JSON: ${jsonObject.toString()}")

            try {
                val response = apiService.removeFromCart("Bearer $accessToken", requestBody)
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Failed to update cart"
                    Log.e("CartController", "API Error: $errorMessage")
                    onResult(false, errorMessage)
                }
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    fun placeOrder(
        onResult: (Boolean, String?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val accessToken = TokenManager.getToken()
            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found")
                return@launch
            }

            try {
                val response = apiService.placeOrder("Bearer $accessToken")
                if (response.isSuccessful) {
                    onResult(true, "Order placed successfully")
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Failed to place order"
                    Log.e("CartController", "API Error: $errorMessage")
                    onResult(false, errorMessage)
                }
            } catch (e: Exception) {
                onResult(false, "Error: ${e.localizedMessage}")
            }
        }
    }
}
