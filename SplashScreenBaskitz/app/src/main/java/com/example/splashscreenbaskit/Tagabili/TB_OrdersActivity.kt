package com.example.splashscreenbaskit.Tagabili

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun TB_OrdersActivityPreview() {
    TB_OrdersContent(navController = rememberNavController())
}

@Composable
fun TB_OrdersContent(navController: NavController) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background (Color.White)
            ){
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(top = 70.dp, start = 25.dp)
                        .size(35.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "ORDER\nSUMMARY",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(top = 140.dp, start = 40.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.orders_img),
                    contentDescription = "orders",
                    modifier = Modifier
                        .padding(start = 210.dp, top = 60.dp)
                        .height(157.dp)
                        .width(160.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 20.dp, bottomStart = 5.dp, bottomEnd = 20.dp))
                )

            }
            //Spacer(modifier = Modifier.height(45.dp))

            Box(modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background (Color(0xFFE0F4DE))
            ){
                Column (
                    modifier = Modifier.fillMaxWidth() .padding(30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    TB_CustomerInfo ()

                    Spacer(modifier = Modifier.height(30.dp))

                    TB_OrderItems()
                }
            }
        }


    }
}

@Composable
fun TB_CustomerInfo (){

    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(30.dp))
            .height(152.dp)
            .width(339.dp)
            .padding(30.dp)
    ){
        Column {
            Row {
                Text(
                    text = "Name:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = "Jorose",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    text = "Branch:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = "Dagupan",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    text = "Contact No.:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = "0900-000-0000",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
fun TB_OrderItems(){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "ITEMS",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 8.dp)
                .background (Color.White, shape = RoundedCornerShape(30.dp))
        ){
            Column (
                modifier = Modifier.fillMaxWidth() .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(193.dp)
                        .background(Color(0xFFD9D9D9), shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "JOROSE STORE",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                }

                ProductDetails()
            }
        }

        Column(
            modifier = Modifier.width(320.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                color = Color.Black,
                thickness = 0.8.dp,
                modifier = Modifier.padding(top = 30.dp, bottom = 20.dp)
            )
        }
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "Total",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = poppinsFontFamily
        )

        Spacer(modifier = Modifier.width(120.dp))

        Text(text = "₱ 00.00",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = poppinsFontFamily
        )
    }
    Spacer(modifier = Modifier.height(40.dp))

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(147.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE22727)),
            enabled = true
        ) {
            Row {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Decline",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Decline",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily
                )
            }
        }


        Spacer(modifier = Modifier.width(25.dp))

        Button(
            modifier = Modifier
                .height(50.dp)
                .width(147.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
            enabled = true
        ) {
            Row {
                Icon(
                    Icons.Default.Done,
                    contentDescription = "Accept",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Accept",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}

@Composable
fun ProductDetails(){
    Row (modifier = Modifier.padding(top = 20.dp)
    ){
        Column {
            Text(text = "Product",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily
            )

            Row {
                Text(text = "1 pc",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily
                )

                Text(text = "Quantity:",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 15.dp)
                )

                Text(text = "1",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        Text(
            text = "₱ 00.00",
            color = Color.DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier.padding(start = 100.dp, top = 10.dp)
        )
    }
}

