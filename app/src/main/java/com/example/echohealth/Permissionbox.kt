package com.example.echohealth
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
    fun PermissionBox(
        textextractor: PermissionTextProvider,
        modifier: Modifier = Modifier,
        ClickOk: () -> Unit,
        ClickSettings: () -> Unit,
       DeniedPermanently: Boolean,
        onDeny: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDeny,
            buttons = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight(0.3f).background(Color.White).padding(1.2.dp).fillMaxWidth()
                ) {
                   // Divider()
                    Text(
                        text = if ( DeniedPermanently) {
                            "allow permission"
                        } else {
                            "ok"
                        },
                           textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(15.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                            .fillMaxHeight(0.3f)
                            .clickable {
                                if (!DeniedPermanently) {
                                    ClickOk()
                                } else {
                                   ClickSettings()
                                }
                            }

                    )
                }
            },
            title = {
                Text(text = "Permission Required")
            },
            text = {
                Text(
                    text = textextractor.messageDisplay(
                        isDeniedPermanently = DeniedPermanently
                    )
                )
            },
            modifier = modifier.padding(1.1.dp)
        )
    }
    abstract class PermissionTextProvider {
        abstract fun messageDisplay(isDeniedPermanently: Boolean): String
    }

    class TextProvider : PermissionTextProvider() {
        override fun messageDisplay(isDeniedPermanently: Boolean): String {

            when(isDeniedPermanently){
                false->{
                    return "This app needs access to your microphone to record audio"
                }

                true->{
                return "It looks like you permanently declined microphone permission. " +
                        " go to the app settings to change it."
                }
            }
        }
    }
