package com.example.splashscreenbaskit.LoginSignup

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}

@Composable
fun AppKo(){
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        LoadingIndicator()
    }
}
@Composable
fun LoadingIndicator(){
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing)
        ), label = ""
    )

    val rotation2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ), label = ""
    )

    val rotation3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer { rotationZ = rotation1 }
                .padding(8.dp),
            strokeWidth = 3.dp,
            color = Color(0xFF1d7151)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(110.dp)
                .graphicsLayer { rotationZ = rotation2 }
                .padding(16.dp),
            strokeWidth = 3.dp,
            color = Color(0xFF1d7151)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer { rotationZ = rotation3 }
                .padding(24.dp),
            strokeWidth = 3.dp,
            color = Color(0xFF1d7151)
        )
    }
}