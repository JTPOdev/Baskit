package com.example.splashscreenbaskit.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.splashscreenbaskit.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay


@Composable
fun SlideImg(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0)
    val pageCount = 3

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        count = pageCount,
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        Image(
            painter = painterResource(
                id = when (page) {
                    0 -> R.drawable.slider1
                    1 -> R.drawable.slider2
                    else -> R.drawable.slider3
                }
            ),
            contentDescription = "Slider Image $page",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
