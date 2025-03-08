package com.example.splashscreenbaskit.AccountDetails

import android.os.Build.VERSION_CODES.R
import android.util.LayoutDirection
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateStorePreview(){
    CreateStore(navController = rememberNavController())
}
@Composable
fun CreateStore(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(315.dp)
                .background(Color(0xFFDBDBDB)),
            contentAlignment = Alignment.BottomStart
        ) {

//            Image(
//                painter = painterResource(id = R.drawable.seller_img),
//                contentDescription = "Apple",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(315.dp),
//                contentScale = ContentScale.Crop
//            )

            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(top = 50.dp, start = 16.dp)
                    .align(Alignment.Center)
                    .size(40.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = com.example.splashscreenbaskit.R.drawable.add_image),
                    contentDescription = "Add",
                    //tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 60.dp, start = 40.dp)
                    .align(Alignment.TopStart)
                    .size(20.dp)
                    .background(Color(0xAAFFFFFF), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = com.example.splashscreenbaskit.R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }

            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ){
                TextButton(onClick = {  }
                ) {
                    Text(
                        "Set store name",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontFamily = poppinsFontFamily)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clickable {
                    // navController.navigate("ProductScreen/${product.name}")
                },
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth() .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "Add a category",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = poppinsFontFamily,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    painterResource(id = com.example.splashscreenbaskit.R.drawable.add_category)
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.example.splashscreenbaskit.R.drawable.editstore_img),
                contentDescription = "Reset Password",
                modifier = Modifier.height(130.dp),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "You can now edit your store!",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                color = Color(0xFF8C8C8C)
            )
        }

    }
}

@Composable
fun LocationSelector(selectedLocation: MutableState<String?>) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        androidx.compose.material.TextButton(onClick = { selectedLocation.value = "Dagupan" }) {
            Text(
                "DAGUPAN",
                color = if (selectedLocation.value == "Dagupan") Color.Black else Color(0xFFBFBFBF),
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontFamily = poppinsFontFamily)
            )
        }
        androidx.compose.material.TextButton(onClick = { selectedLocation.value = "Calasiao" }) {
            Text(
                "CALASIAO",
                color = if (selectedLocation.value == "Calasiao") Color.Black else Color(0xFFBFBFBF),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontFamily = poppinsFontFamily)
            )
        }
    }
}