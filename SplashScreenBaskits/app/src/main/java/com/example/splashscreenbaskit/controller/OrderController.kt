package com.example.splashscreenbaskit.controllers

import AcceptOrderRequest
import Order
import OrderResponse
import ReadyOrderRequest
import TotalOrdersResponse
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class OrderController(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) {

    init {
        TokenManager.init(context)
    }

    fun fetchOrders(
        onResult: (Boolean, String?, List<Order>?) -> Unit,
        location: String? = null
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getOrders("Bearer $accessToken")

                if (response.isSuccessful) {
                    val ordersResponse = response.body()

                    if (ordersResponse != null) {
                        val orders = ordersResponse.orders

                        // Apply filter if location is provided
                        val filteredOrders = if (!location.isNullOrEmpty()) {
                            orders.filter {
                                it.product_origin.contains(
                                    location,
                                    ignoreCase = true
                                )
                            }
                        } else {
                            orders
                        }

                        saveOrdersLocally(filteredOrders)
                        onResult(true, null, filteredOrders)
                    } else {
                        Log.e("OrderController", "No valid orders found")
                        onResult(false, "No orders available", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("OrderController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch orders", null)
                }
            } catch (e: HttpException) {
                Log.e("OrderController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("OrderController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    fun fetchAcceptedOrders(
        onResult: (Boolean, String?, List<Order>?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getAcceptedOrders("Bearer $accessToken")
                if (response.isSuccessful) {
                    val ordersResponse = response.body()
                    if (ordersResponse != null) {
                        val orders = ordersResponse.orders

                        saveOrdersLocally(orders)
                        onResult(true, null, orders)
                    } else {
                        Log.e("AcceptedOrdersController", "No valid orders found")
                        onResult(false, "No orders available", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AcceptedOrdersController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch orders", null)
                }
            } catch (e: HttpException) {
                Log.e("AcceptedOrdersController", "HTTP Error: ${e.message()}")
                onResult(false, "Server error: ${e.message()}", null)
            } catch (e: Exception) {
                Log.e("AcceptedOrdersController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    private fun saveOrdersLocally(orders: List<Order>) {
        val sharedPreferences = context.getSharedPreferences("OrdersPrefs", Context.MODE_PRIVATE)
        val ordersJson = Gson().toJson(orders)
        sharedPreferences.edit().apply {
            putString("orders", ordersJson)
            apply()
        }
    }

    fun fetchTotalOrdersByLocation(
        onResult: (Boolean, String?, TotalOrdersResponse?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found", null)
                return@launch
            }

            try {
                val response = apiService.getTotalOrdersByLocation("Bearer $accessToken")

                if (response.isSuccessful) {
                    val totals = response.body()
                    if (totals != null) {
                        onResult(true, null, totals)
                    } else {
                        onResult(false, "Failed to retrieve totals", null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("OrderController", "API Error: $errorBody")
                    onResult(false, "Failed to fetch total orders", null)
                }
            } catch (e: Exception) {
                Log.e("OrderController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}", null)
            }
        }
    }

    suspend fun fetchUserOrders(userId: Int): List<Order>? {
        return try {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                Log.e("OrderController", "No access token found")
                return null
            }

            val response = apiService.getUserOrders("Bearer $accessToken", userId)

            if (response.isSuccessful) {
                response.body()?.orders?.orders
            } else {
                Log.e("OrderController", "Failed to fetch orders: ${response.message()}")
                null
            }
        } catch (e: HttpException) {
            Log.e("OrderController", "HTTP Error: ${e.message()}")
            null
        } catch (e: Exception) {
            Log.e("OrderController", "Unexpected Error: ${e.localizedMessage}")
            null
        }
    }

    fun acceptOrder(orderCode: String, userId: Int, onResult: (Boolean, String?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found")
                return@launch
            }

            try {
                val response = apiService.acceptOrder("Bearer $accessToken", AcceptOrderRequest(orderCode, userId))
                if (response.isSuccessful) {
                    onResult(true, "Order accepted successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("OrderController", "API Error: $errorBody")
                    onResult(false, "Failed to accept order")
                }
            } catch (e: Exception) {
                Log.e("OrderController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun readyOrder(orderCode: String, userId: Int, onResult: (Boolean, String?) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val accessToken = TokenManager.getToken()

            if (accessToken.isNullOrEmpty()) {
                onResult(false, "No access token found")
                return@launch
            }

            try {
                val response = apiService.readyOrder("Bearer $accessToken", ReadyOrderRequest(orderCode))
                if (response.isSuccessful) {
                    onResult(true, "Order accepted successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("OrderController", "API Error: $errorBody")
                    onResult(false, "Failed to accept order")
                }
            } catch (e: Exception) {
                Log.e("OrderController", "Exception: ${e.localizedMessage}")
                onResult(false, "Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}

