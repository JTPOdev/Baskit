package com.example.splashscreenbaskit.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.example.splashscreenbaskit.controller.AnnouncementController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SlideImg(modifier: Modifier = Modifier) {
    val pageCount = 3
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )

    val coroutineScope = rememberCoroutineScope()
    val controller = remember { AnnouncementController() }

    var slideImages by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        val response = controller.fetchAnnouncementImages()
        response?.let {
            slideImages = listOf(it.slideimage_1, it.slideimage_2, it.slideimage_3)
            Log.d("SlideImg", "Images Loaded: $slideImages")
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        val actualPage = page % pageCount

        if (slideImages.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(slideImages[actualPage]),
                contentDescription = "Slider Image $actualPage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
