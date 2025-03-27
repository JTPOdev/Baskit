import android.graphics.Bitmap
import android.net.Uri
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.Serializable

data class LoginRequest(
    val username_or_email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val access_token: String,
    val role: String
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

data class CartResponse(
    val success: Boolean,
    val message: String
)
data class CartItem(
    val product_id: Int,
    val product_name: String,
    val product_price: Double,
    val fee: Double,
    val product_quantity: Int,
    val product_portion: String,
    val product_origin: String?,
    val store_id: Int,
    val store_name: String,
    val product_image: String?,
    val order_status: String,
    val status: String,
    val tagabili_firstname: String,
    val tagabili_lastname: String,
    val tagabili_mobile: String,
    val tagabili_email: String,
    val order_code: String?,
    val is_ready: String
)

data class StoreRequestResponse(
    val success: Boolean,
    val message: String,
    val data: StoreResponse?
)

data class UploadImageResponse(
    val success: Boolean,
    val imageUrl: String
)

data class ProductResponse(
    val message: String
)

data class ProductsResponse(
    val id: Int,
    val product_name: String,
    val product_price: String,
    val product_category: String,
    val product_origin: String,
    val product_image: String?,
    val store_id: Int,
    val store_name: String,
    val store_phone_number: String,
    val store_address: String,
    val store_image: String?
)

data class Product(
    val name: String,
    val price: Double?,
    val imageUri: Uri?,
    val category: String?
)

data class OrderResponses(
    val orders: OrdersWrapper
)

data class OrdersWrapper(
    val total_orders: Int,
    val orders: List<Order>
)

data class OrderResponse(
    val orders: List<Order>
)

data class Order(
    val id: Int,
    val user_id: Int,
    val product_id: Int,
    val product_name: String,
    val product_price: String,
    val fee: Double,
    val product_quantity: Int,
    val product_portion: String,
    val product_origin: String,
    val store_id: Int,
    val store_name: String,
    val product_image: String,
    val tagabili_firstname: String,
    val tagabili_lastname: String,
    val tagabili_mobile: String,
    val tagabili_email: String,
    val status: String,
    val order_code: String,
    val created_at: String,
    val firstname: String,
    val lastname: String,
    val mobile_number: String,
    val total_orders: Int,
    val is_ready: String
)


data class TotalOrdersResponse(
    val total_dagupan_orders: Int,
    val total_calasiao_orders: Int
)

data class AcceptOrderRequest(
    val order_code: String,
    val user_id: Int
)

data class ReadyOrderRequest(
    val order_code: String
)

data class Announcement(
    val slideimage_1: String,
    val slideimage_2: String,
    val slideimage_3: String
)