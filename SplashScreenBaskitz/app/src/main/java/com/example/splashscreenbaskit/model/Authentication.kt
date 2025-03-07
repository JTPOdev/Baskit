import android.net.Uri
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val username_or_email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val access_token: String,
    val role: String
)

data class LogoutRequest(
    val access_token: String,
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val mobile_number: String,
    val password: String,
    val confirm_password: String,
    val firstname: String,
    val lastname: String,
    val birth_month: String,
    val birth_day: String,
    val birth_year: String,
    val is_mobile: Boolean
)

data class RegisterResponse(
    val message: String
)


data class UserResponse(
    val status: String,
    val user: UserData?
)

data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val mobile_number: String,
    val firstname: String,
    val lastname: String,
    val age: Int?,
    val role: String,
    val is_verified: Boolean
)

data class User(
    val name: String,
    val username: String,
    val email: String,
    val contactNumber: String,
    val password: String = "********",
    val role: String
)

data class StoreResponse(
    val id: String,
    val user_id: String,
    val store_name: String,
    val owner_name: String,
    val store_phone_number: String,
    val store_address: String,
    val store_origin: String,
    val store_rating: String,
    val store_status: String,
    val registered_store_name: String,
    val registered_store_address: String,
    val certificate_of_registration: String,
    val valid_id: String,
    val store_image: String?,
    val created_at: String
)


data class StoreRequestResponse(
    val success: Boolean,
    val message: String,
    val data: StoreResponse?
)

