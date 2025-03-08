package com.example.splashscreenbaskit.controller

import LoginRequest
import LoginResponse
import RetrofitInstance
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class LoginController(private val lifecycleOwner: LifecycleOwner, private val context: Context) {

    init {
        TokenManager.init(context)
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String, String?, Map<String, String>) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val loginRequest = LoginRequest(username_or_email = email, password = password)

            try {
                val response: Response<LoginResponse> = apiService.login(loginRequest)
                Log.d("LoginResponse", "Response body: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()

                    Log.d("LoginResponse", "Access Token: ${loginResponse?.access_token}")
                    Log.d("LoginResponse", "Role: ${loginResponse?.role}")

                    val token = loginResponse?.access_token
                    val role = loginResponse?.role

                    if (token != null) {
                        TokenManager.saveToken(token)
                    }
                    onResult(true, loginResponse?.message ?: "Login Successful!", role, emptyMap())
                } else {
                    val errors = response.errorBody()?.let { parseErrors(it) } ?: emptyMap()
                    val errorMessage = errors["message"] ?: "Login Failed"
                    onResult(false, errorMessage, null, errors)
                }
            } catch (e: HttpException) {
                Log.e("LoginError", e.toString())
                onResult(false, "An error occurred: ${e.message()}", null, emptyMap())
            } catch (e: Exception) {
                Log.e("LoginError", e.toString())
                onResult(false, "Unexpected error: ${e.localizedMessage}", null, emptyMap())
            }
        }
    }

    private fun parseErrors(errorBody: ResponseBody): Map<String, String> {
        val errorsMap = mutableMapOf<String, String>()

        try {
            val json = errorBody.string()
            val jsonObject = JSONObject(json)

            val errorsJson = jsonObject.optJSONObject("errors")
            if (errorsJson != null) {
                errorsJson.keys().forEach { key ->
                    val errorMessage = errorsJson.optString(key)
                    val errorLines = errorMessage.replace("\n", " ").split(",").map { it.trim() }
                    val formattedMessage = errorLines.joinToString("\n")
                    errorsMap[key] = formattedMessage
                }
            } else {
                val errorMessage = jsonObject.optString("message", "")
                if (errorMessage.isNotEmpty()) {
                    errorsMap["message"] = errorMessage
                }
            }
        } catch (e: Exception) {
            Log.e("LoginError", "Error parsing response: ${e.localizedMessage}")
        }

        return errorsMap
    }
}
