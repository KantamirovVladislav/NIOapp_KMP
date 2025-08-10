package com.notebook.nioapp.presentation.urlSelectionScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.notebook.nioapp.domain.repository.DirectorySelectionComponent
import com.notebook.nioapp.domain.repository.UrlSelectionComponent
import com.notebook.nioapp.presentation.common.FilePathPresenter
import com.notebook.nioapp.presentation.common.UrlCheckPresenter

@Composable
fun UrlSelectionScreen(
    component: UrlSelectionComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()
    val url by component.url.subscribeAsState()
    val checkResult by component.checkUrlResult.subscribeAsState()
    Scaffold {
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
                                when (state) {
                                    is UrlSelectionComponent.UrlState.Loading -> {
                                        "Loading"
                                    }

                                    is UrlSelectionComponent.UrlState.Configured -> {
                                        "Setup url is confirm"
                                    }

                                    is UrlSelectionComponent.UrlState.Error -> {
                                        (state as UrlSelectionComponent.UrlState.Error).message
                                    }

                                    is UrlSelectionComponent.UrlState.NeedConfiguration -> {
                                        "Please, enter setup url or choose 'Continue without setup url'"
                                    }
                                },
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            UrlCheckPresenter(
                                url = url,
                                checkResult = checkResult,
                                onUrlChange = component::onUrlChange,
                            )

                            Button(
                                onClick = component::confirmUrlSelection,
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                enabled = checkResult
                            ) {
                                Text("Continue")
                            }

                            Button(
                                onClick = component::confirmWithoutUrl,
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                shape = RoundedCornerShape(corner = CornerSize(8.dp))
                            ) {
                                Text("Continue without setup url")
                            }
                        }
                    }
                }
            }
        }
    }
}