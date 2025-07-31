package com.notebook.nioapp.presentation.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.compose.NIOTheme
import com.notebook.nioapp.domain.repository.OnBoardingComponent
import com.notebook.nioapp.presentation.common.CirclesProgressIndicator

@Composable
fun OnBoardingScreen(
    component: OnBoardingComponent,
    modifier: Modifier = Modifier
) {
    NIOTheme {
        val currentPage by component.currentPage.subscribeAsState()
        val pagerState = rememberPagerState(initialPage = currentPage) { 3 }

        LaunchedEffect(currentPage) {
            pagerState.animateScrollToPage(currentPage)
        }

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                component.onPageChanged(pagerState.currentPage)
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> onBoardingElement(
                    progress = page,
                    progressCount = 3,
                    title = "Welcome to NIO",
                    content = "Discover amazing features that will help you in your daily tasks.",
                    onNextClicked = component::onNextClicked,
                )

                1 -> onBoardingElement(
                    progress = page,
                    progressCount = 3,
                    title = "Easy to Use",
                    content = "Our intuitive interface makes it simple to get started.",
                    onNextClicked = component::onNextClicked,
                )

                2 -> onBoardingElement(
                    progress = page,
                    progressCount = 3,
                    title = "Get Started",
                    content = "Join thousands of satisfied users today.",
                    onNextClicked = component::onNextClicked,
                )
            }
        }
    }
}

@Composable
fun onBoardingElement(
    progress: Int = 0,
    progressCount: Int = 3,
    title: String,
    content: String,
    onNextClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .offset(y = (-16).dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(36.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Unspecified.copy(alpha = 0.8f),
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        Text(
                            text = content,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp)
                        ) {
                            Button(
                                onClick = onNextClicked,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(corner = CornerSize(8.dp))
                            ) {
                                Text(text = if (progress < progressCount - 1) "Next" else "Get Started")
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                CirclesProgressIndicator(
                    progress = progress,
                    maxCount = progressCount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}