package com.example.splashscreenbaskit.AccountDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
@Preview(showBackground = true)
@Composable
fun RulesScreenPreview() {
    RulesScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    var isScrolledToEnd by remember { mutableStateOf(false) }
    var hasReachedBottom by remember { mutableStateOf(false) }

    val rules = listOf(
        "1. Users must provide accurate information when registering and making purchases.",
        "2. Orders must be confirmed before generating a unique code for in-store pickup.",
        "3. Generated codes are valid only within the specified time frame."
    )


    LaunchedEffect(scrollState.value, scrollState.maxValue) {
        if (scrollState.maxValue > 0) {
            hasReachedBottom = scrollState.value >= (scrollState.maxValue - 10)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "< Back",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier
                .padding(top = 50.dp, start = 30.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Start Your Own Store",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE0F4DE))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rules_img),
                        contentDescription = "Rules",
                        modifier = Modifier.height(180.dp).align(Alignment.CenterHorizontally) .padding(top = 10.dp, bottom = 40.dp),
                    )

                    Text(
                        text = "Rules and Regulations",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    rules.forEach { rule ->
                        TextItem(text = rule)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            //perks section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF7FAE2))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shops_img),
                        contentDescription = "Rules",
                        modifier = Modifier.height(180.dp).align(Alignment.CenterHorizontally) .padding(top = 10.dp, bottom = 40.dp),
                    )

                    Text(
                        text = "Perks of Standard and Partnership Shops",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Standard Shops",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    rules.forEach { rule ->
                        TextItem(text = rule)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Partnership Shops",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    rules.forEach { rule ->
                        TextItem(text = rule)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            //punishment section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE4F7FF))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.punishment_img),
                        contentDescription = "Rules",
                        modifier = Modifier.height(180.dp).align(Alignment.CenterHorizontally) .padding(top = 10.dp, bottom = 40.dp),
                    )

                    Text(
                        text = "Punishment for Offenses",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    rules.forEach { rule ->
                        TextItem(text = rule)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .width(250.dp)
                        .align(Alignment.Center),
                    onClick = { navController.navigate("StoreRequestScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
                    enabled = hasReachedBottom
                ) {
                    Text(
                        text = "I accept & understand",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily
                    )
                }
            }

        }
    }
}
@Composable
fun TextItem(text: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}
