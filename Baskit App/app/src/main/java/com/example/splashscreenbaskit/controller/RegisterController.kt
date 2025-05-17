package com.example.splashscreenbaskit.controller

import RegisterRequest
import RegisterResponse
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

class RegisterController(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) {

    init {
        TokenManager.init(context)
    }

    fun register(
        firstName: String,
        lastName: String,
        userName: String,
        contactNumber: String,
        email: String,
        password: String,
        confirmPassword: String,
        birthMonth: String,
        birthDay: String,
        birthYear: String,
        onResult: (Boolean, String, Map<String, String>) -> Unit
    ) {

        if (password != confirmPassword) {
            onResult(false, "Passwords do not match!", emptyMap())
            return
        }

        val registerData = RegisterRequest(
            username = userName,
            email = email,
            mobile_number = contactNumber,
            password = password,
            confirm_password = confirmPassword,
            firstname = firstName,
            lastname = lastName,
            birth_month = birthMonth,
            birth_day = birthDay,
            birth_year = birthYear,
            is_mobile = true
        )

        lifecycleOwner.lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.register(registerData)

                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "User registered successfully"
                    onResult(true, message, emptyMap())
                } else {
                    val errorMessage = when {
                        response.message().contains(
                            "Username already taken",
                            ignoreCase = true
                        ) -> "Username already taken"

                        response.message().contains(
                            "Email already taken",
                            ignoreCase = true
                        ) -> "Email already in use"

                        response.message().contains(
                            "Mobile number already registered",
                            ignoreCase = true
                        ) -> "Contact number already registered"

                        else -> "Registration failed: ${response.message()}"
                    }
                    val errors = response.errorBody()?.let { parseErrors(it) } ?: emptyMap()
                    val passwordError = errors["password"]

                    if (!passwordError.isNullOrEmpty()) {
                        val errorLines = passwordError.split("\n")
                        for (line in errorLines) {
                            println(line.trim())
                        }
                    }

                    onResult(false, errorMessage, errors)
                }
            } catch (e: HttpException) {
                Log.e("RegisterError", e.toString())
                onResult(
                    false,
                    "An error occurred: ${e.message()}",
                    emptyMap()
                )
            } catch (e: Exception) {
                Log.e("RegisterError", e.toString())
                onResult(false, "Unexpected error: ${e.localizedMessage}", emptyMap())
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
                    var errorMessage = errorsJson.optString(key)
                    val errorLines = errorMessage.replace("\n", " ").split(",").map { it.trim() }
                    val formattedMessage = errorLines.joinToString("\n")
                    errorsMap[key] = formattedMessage
                }
            } else {
                val errorMessage = jsonObject.optString("message", "")

                if (errorMessage.isNotEmpty()) {
                    errorsMap["general"] = errorMessage
                }
            }
        } catch (e: Exception) {
            Log.e("RegisterError", "Error parsing error response: ${e.localizedMessage}")
        }
        return errorsMap
    }
}


