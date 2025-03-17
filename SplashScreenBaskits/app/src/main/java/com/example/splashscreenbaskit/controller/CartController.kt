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
            product_image = productImageUrl
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
                    if (!cartList.isNullOrEmpty()) {
                        saveCartLocally(cartList)
                        onResult(true, null, cartList)
                    } else {
                        Log.e("CartController", "Cart is empty")
                        onResult(false, "No items in cart", null)
                    }
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
                onResult(true, "", null)
            }
        }
    }

    private fun saveCartLocally(cartList: List<CartItem>) {
        val prefs = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE).edit()
        val json = Gson().toJson(cartList)
        prefs.putString("cart_data", json)
        prefs.apply()
    }
}
