package com.example.animationpilot.effects

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animationpilot.enum.MyImages

@ExperimentalAnimationApi
@Composable
fun Fade() {
    var state by rememberSaveable { mutableStateOf(MyImages.Android.image) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Button(
            onClick = {
                if (state == MyImages.Android.image)
                    state = MyImages.Apple.image
                else
                    state = MyImages.Android.image
            }
        ) {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    fadeIn(animationSpec = tween(1000, 1000)) with
                            fadeOut(animationSpec = tween(1000))
                }
            ) { state ->
                Image(
                    painter = painterResource(id = state),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        }
    }

}

