package com.example.splashscreenbaskit.LoginSignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.times
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.blur_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Onboarding Pager
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = maxHeight * 0.05f)
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OnboardingImage(page, maxWidth, maxHeight)
                OnboardingContent(
                    title = when (page) {
                        0 -> "Welcome"
                        1 -> "Shop Smarter,\n \nNot Harder"
                        else -> "One-stop Pickup"
                    },
                    description = when (page) {
                        0 -> "Enjoy a fun experience\nof personalized market shopping!"
                        1 -> "Add items to your basket and let\nour Tagabili do the work for you"
                        else -> "Pick your orders up from\nour branches in one-go!"
                    },
                    maxWidth = maxWidth,
                    maxHeight = maxHeight
                )
            }
        }

        // Skip Button (Bottom Left)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = maxWidth * 0.05f, bottom = maxHeight * 0.05f),
            contentAlignment = Alignment.BottomStart
        ) {
            TextButton(
                onClick = {
                    coroutineScope.launch { pagerState.scrollToPage(2) }
                }
            ) {
                Text(
                    text = "Skip",
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        // Page Indicator
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageIndicator(
                currentPage = pagerState.currentPage,
                totalScreens = 3,
                maxHeight = maxHeight
            )
        }

        // Get Started Button (only on last page)
        if (pagerState.currentPage == 2) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = maxWidth * 0.05f, bottom = maxHeight * 0.05f),
                contentAlignment = Alignment.BottomEnd
            ) {
                TextButton(
                    onClick = { navController.navigate("LoginActivity") }
                ) {
                    Text(
                        text = "Get Started",
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingImage(page: Int, maxWidth: Dp, maxHeight: Dp) {
    val imageRes = when (page) {
        0 -> R.drawable.onboarding_img1
        1 -> R.drawable.onboarding_img2
        else -> R.drawable.onboarding_img3
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeight * 0.4f)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun OnboardingContent(title: String, description: String, maxWidth: Dp, maxHeight: Dp) {
    Column(
        modifier = Modifier
            .padding(horizontal = maxWidth * 0.1f)
            .heightIn(min = maxHeight * 0.3f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(maxHeight * 0.02f)
    ) {
        Text(
            text = title,
            fontFamily = poppinsFontFamily,
            style = TextStyle(
                fontSize = maxWidth.value * 0.10f.sp,
                lineHeight = maxWidth.value * 0.045f.sp
            ),
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            fontFamily = poppinsFontFamily,
            fontSize = maxWidth.value * 0.05f.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PageIndicator(currentPage: Int, totalScreens: Int, maxHeight: Dp) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(maxHeight * 0.005f, Alignment.CenterHorizontally),
        modifier = Modifier
            .padding(top = maxHeight * 0.01f, bottom = maxHeight * 0.1f)
            .fillMaxWidth()
    ) {
        repeat(totalScreens) { index ->
            Box(
                modifier = Modifier
                    .size(maxHeight * 0.015f)
                    .background(
                        color = if (index == currentPage) Color(0xFF3F79FB) else Color(0xFFD9D9D9),
                        shape = CircleShape
                    )
            )
            if (index < totalScreens - 1) {
                Spacer(modifier = Modifier.width(maxHeight * 0.01f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())
}