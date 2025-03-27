package com.example.splashscreenbaskit.api

import AcceptOrderRequest
import Announcement
import CartItem
import CartResponse
import LoginRequest
import LoginResponse
import Order
import OrderResponse
import OrderResponses
import Product
import ProductResponse
import ProductsResponse
import ReadyOrderRequest
import RegisterRequest
import RegisterResponse
import StoreRequestResponse
import StoreResponse
import TotalOrdersResponse
import UploadImageResponse
import UserResponse
import com.example.splashscreenbaskit.Home.Vendor
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //---------- USER AUTHENTICATION AND DETAILS ---------//
    @POST("/user/register")
    suspend fun register(
        @Body registerData: RegisterRequest
    ): Response<RegisterResponse>

    @POST("/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/user/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<Void>

    @GET("user/details")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    //---------- STORE ---------//
    @Multipart
    @POST("store/create")
    suspend fun sendRequest(
        @Part("user_id") userId: RequestBody,
        @Part("store_name") storeName: RequestBody,
        @Part("owner_name") ownerName: RequestBody,
        @Part("store_phone_number") storePhone: RequestBody,
        @Part("store_address") storeAddress: RequestBody,
        @Part("store_origin") storeOrigin: RequestBody,
        @Part("registered_store_name") registeredStoreName: RequestBody,
        @Part("registered_store_address") registeredStoreAddress: RequestBody,
        @Part("store_status") storeStatus: RequestBody,
        @Part certificate: MultipartBody.Part?,
        @Part validId: MultipartBody.Part?
    ): Response<StoreRequestResponse>

    @GET("store/all")
    suspend fun getStoreDetails(
        @Header("Authorization") authorization: String
    ): Response<List<StoreResponse>>

    @GET("store/mystore")
    suspend fun getMyStoreDetails(
        @Header("Authorization") authorization: String
    ): Response<StoreResponse>

    @GET("store/user/{user_id}")
    suspend fun getStoreDetailsUserID(
        @Header("Authorization") authorization: String,
        @Path("user_id") userId: Int
    ): Response<StoreResponse>

    @Multipart
    @POST("store/image")
    suspend fun uploadStoreImage(
        @Header("Authorization") token: String,
        @Part storeImage: MultipartBody.Part
    ): Response<UploadImageResponse>

    @GET("store/products")
    suspend fun getProductDetails(
        @Header("Authorization") authorization: String
    ): Response<List<ProductsResponse>>

    @GET("store/product/{id}")
    suspend fun getProductDetailsByID(
        @Path("id") storeId: Int
    ): Response<List<ProductsResponse>>


    //---------- PRODUCT ---------//
    @GET("/products")
    suspend fun getProductsByOrigin(
        @Header("Authorization") authorization: String,
        @Query("product_origin") productOrigin: String
    ): Response<List<ProductsResponse>>

    @Multipart
    @POST("product/create")
    fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_price") productPrice: RequestBody,
        @Part("product_category") productCategory: RequestBody,
        @Part productImage: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<ProductResponse>

    //---------- HOME ---------//
    @GET("vendors/dagupan")
    suspend fun getDagupanVendors(): List<Vendor>

    @GET("vendors/calasiao")
    suspend fun getCalasiaoVendors(): List<Vendor>

    //---------- CART ---------//
    @POST("/cart/add")
    fun addToCart(
        @Body cartItem: CartItem
    ): Call<CartResponse>

    @PUT("/cart/update")
    suspend fun updateCart(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Response<CartItem>

    @HTTP(method = "DELETE", path = "/cart/remove", hasBody = true)
    suspend fun removeFromCart(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Response<CartItem>

    @GET("cart/view")
    suspend fun getCartItems(
        @Header("Authorization") authToken: String
    ): Response<List<CartItem>>

    //---------- ORDER ---------//
    @POST("/order/place") // Place Order (check out)
    suspend fun placeOrder(
        @Header("Authorization") token: String
    ): Response<CartItem>

    @GET("/all/orders") // User info & Total orders (specific)
    suspend fun getOrders(
        @Header("Authorization") accessToken: String
    ): Response<OrderResponse>

    @GET("orders/total/origin") // Order counter
    suspend fun getTotalOrdersByLocation(
        @Header("Authorization") token: String
    ): Response<TotalOrdersResponse>

    @GET("/orders")
    suspend fun getUserOrders(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int
    ): Response<OrderResponses>

    @POST("order/accept")
    suspend fun acceptOrder(
        @Header("Authorization") token: String,
        @Body request: AcceptOrderRequest
    ): Response<Unit>

    @POST("order/ready")
    suspend fun readyOrder(
        @Header("Authorization") token: String,
        @Body request: ReadyOrderRequest
    ): Response<Unit>

    @GET("accepted/orders")
    suspend fun getAcceptedOrders(
        @Header("Authorization") token: String
    ): Response<OrderResponse>

    @GET("announcement/all/images")
    suspend fun getSlideImages(): Announcement
}







