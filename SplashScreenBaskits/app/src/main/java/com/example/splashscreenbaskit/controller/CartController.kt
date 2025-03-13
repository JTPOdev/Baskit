package com.example.splashscreenbaskit.controller

import CartItem
import CartResponse
import com.example.splashscreenbaskit.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartController(private val apiService: ApiService) {

    fun addToCart(
        productId: Int,
        productQuantity: Int,
        productPortion: String,
        productImageUrl: String,
        callback: (CartResponse) -> Unit
    ) {
        val cartItem = CartItem(
            product_id = productId,
            product_quantity = productQuantity,
            product_portion = productPortion,
            product_image_url = productImageUrl
        )

        apiService.addToCart(cartItem).enqueue(object : retrofit2.Callback<CartResponse> {
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
}

