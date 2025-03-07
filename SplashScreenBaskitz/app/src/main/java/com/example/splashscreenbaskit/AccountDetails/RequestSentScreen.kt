package com.example.splashscreenbaskit.AccountDetails

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview
@Composable
fun RequestPreview (){
    RequestSentScreen(navController = rememberNavController())
}
@Composable
fun RequestSentScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Column (
            modifier = Modifier.fillMaxWidth() .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1D7151), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done",
                        tint = Color.White
                    )
                }
                Divider(
                    color = Color(0xFF1D7151),
                    modifier = Modifier
                        .width(60.dp)
                        .height(2.dp)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1D7151), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(70.dp))


            Image(
                painter = painterResource(id = R.drawable.request_img),
                contentDescription = "Request Sent Illustration",
                modifier = Modifier.height(230.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))


            Text("Request sent!",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Your information is being verified.\nThis may take a few days. Please wait for\nupdates to be sent through your email.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // OK Button
            Button(
                onClick = {
                    navController.navigate("AccountActivity") {
                        popUpTo("RequestSentScreen") { inclusive = true }
                    }},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("OK", color = Color.White)
            }
        }
    }

}
