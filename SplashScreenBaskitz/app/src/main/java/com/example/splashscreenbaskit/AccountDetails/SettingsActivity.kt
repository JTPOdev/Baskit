package com.example.splashscreenbaskit.AccountDetails

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
    ){

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 45.dp, start = 25.dp)
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

            Image(
                painter = painterResource(id = R.drawable.settings_img),
                contentDescription = null,
                modifier = Modifier
                    .height(170.dp)
                    .padding( start = 20.dp)
                    .offset(x = 60.dp,y = (100).dp)
            )

            // Inner Box
            Box(
                modifier = Modifier
                    .height(680.dp)
                    .width(420.dp)
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
                }
            }
        }
    }

    Column(modifier = Modifier.padding(top = 400.dp, start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center
    ) {


        Spacer(modifier = Modifier.height(40.dp))

        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F1F1), RoundedCornerShape(20.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
                    ) {
                        Text(
                            text = "Receive push notifications",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            modifier = Modifier
                                .padding(start = 50.dp)
                                .size(30.dp),
                            colors = SwitchDefaults.colors(
                                //checkedThumbColor = Color.Green,
                                //uncheckedThumbColor = Color.Red,
                                checkedTrackColor = Color.DarkGray,
                                uncheckedTrackColor = Color.LightGray
                            )
                        )

                    }
                }

            }
        }
    }
}

