package com.example.splashscreenbaskit.AccountDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily


@Preview(showBackground = true)
@Composable
fun SettingsActivityPreview() {
    SettingsActivity(navController = rememberNavController())
}

@Composable
fun SettingsActivity(navController: NavHostController) {
    var notificationsEnabled by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 60.dp, start = 25.dp)
                    .size(35.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Image(
                    painter = painterResource(id = R.drawable.settings_img),
                    contentDescription = null,
                    modifier = Modifier.height(170.dp)
                )
            }

            // Inner Box
            Box(
                modifier = Modifier
                    .height(680.dp)
                    .fillMaxWidth()
                    .padding(top = 160.dp)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .align(Alignment.BottomCenter)
                    .background(Color(0xFF1D7151))
            ) {

                Column(
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, top = 50.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = poppinsFontFamily
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(20.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Receive push notifications",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppinsFontFamily
                            )

                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it },
                                modifier = Modifier
                                    .padding(start = 30.dp)
                                    .size(30.dp),
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = Color(0xFFB9B9B9),
                                    uncheckedTrackColor = Color.LightGray
                                )
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "About Us",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = poppinsFontFamily
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(20.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                        ) {
                            Text(
                                text = "Baskit is a shopping app that simplifies local market shopping by allowing users to create grocery lists, generate a unique code, and quickly pick up their items in-store. The project aims to enhance convenience, streamline shopping, and improve customer satisfaction.",
                                fontFamily = poppinsFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Contact Us",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = poppinsFontFamily
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxSize()
                            .background(Color.White, RoundedCornerShape(20.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(
                                    text = "0900-000-0000",
                                    fontFamily = poppinsFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }

                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(
                                    text = "baskitofficial@gmail.com",
                                    fontFamily = poppinsFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(
                                    text = "Somewhere",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = poppinsFontFamily
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

