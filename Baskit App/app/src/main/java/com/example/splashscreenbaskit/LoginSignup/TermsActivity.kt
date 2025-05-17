import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

/*package com.example.splashscreenbaskit.LoginSignup

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun TermsActivityPreview() {
    TermsActivity()
}


data class BottomNavItem(
    val title : String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    //val hasNews: Boolean,
    //val badgeCount: Int? = null
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TermsActivity() {

    val items = listOf(
        BottomNavItem (
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            //hasNews = false
        ),
        BottomNavItem (
            title = "Basket",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            //hasNews = false
        ),
        BottomNavItem (
            title = "Account",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            //hasNews = false
        ),
    )
    var selectedIconIndex by rememberSaveable { mutableStateOf(0) }
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold {
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed{ index, item ->
                        NavigationBarItem(
                            selected = selectedIconIndex == index ,
                            onClick = {selectedIconIndex = index},
                            icon = {}
                        )
                    }
                }

            }
        }
    }
    
    Column(
        modifier = Modifier
            .padding(top = 70.dp, start = 40.dp, end = 40.dp, bottom = 80.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Terms and Conditions",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Welcome to Baskit!\nThese Terms and Conditions govern your use\nof our delivery app and services.".trimIndent(),
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text (text = "Terms",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = """
                      You must be at least 18 years old to use Baskit and agree to provide accurate personal information when creating an account.
                      You are responsible for maintaining the confidentiality of your login details.
                      Orders placed through the app are subject to availability and acceptance by merchants.
                      Prices displayed on the app include applicable charges unless stated otherwise, and payment must be completed before order confirmation.

                      Delivery times are estimated and may vary due to unforeseen circumstances.
                      Users must provide accurate delivery addresses, and if a recipient is unavailable, the order may be canceled or rescheduled at the user’s cost.
                      Orders can only be canceled before they are accepted by the merchant.
                      Refunds, if applicable, will be processed according to Baskit’s refund policy.

                      Users must not misuse the app, engage in fraud, or harass others.
                      Baskit reserves the right to suspend or terminate accounts that violate these terms.
                      We act as an intermediary between users and merchants and are not responsible for product quality.
                      Additionally, we are not liable for delays or losses due to factors beyond our control.

                      By using Baskit, you agree to our Privacy Policy regarding data collection and usage.
                      We reserve the right to update these terms at any time, and continued use of the app signifies acceptance of any modifications.
                      If you have any questions or concerns, please contact us at Baskit.
                      """.trimIndent(),
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Black
        )

    }
}

class NavBarItems {

}

@Preview
@Composable

fun MyBasketActivity () {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                //.padding(horizontal = 30.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "My Basket",
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )


            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(top = 45.dp, start = 25.dp)
                    //.align(Alignment.TopStart)
                    .size(35.dp)
                    .background(Color.White, shape = RoundedCornerShape(50))
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
    }
}*/