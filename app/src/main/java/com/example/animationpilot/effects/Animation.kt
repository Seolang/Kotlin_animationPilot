package com.example.animationpilot.effects

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.KeyFrames
import com.example.animationpilot.enum.MyImages

@Composable
fun Dissolve() {
    var state by rememberSaveable { mutableStateOf(MyImages.Android) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Row {
            MyImages.values().forEach { myImages ->
                Button(
                    onClick = { state = myImages },
                    modifier = Modifier
                        .weight(1f, true)
                        .height(48.dp)
                        .padding(4.dp)
                ) {
                    Text(myImages.name)
                }
            }
        }
        Crossfade(targetState = state, animationSpec = tween(3000)) { selectedImage ->
            Box(

            ) {
                Image(
                    painter = painterResource(id = selectedImage.image),
                    contentDescription = selectedImage.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        }

    }
}

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
            ) { curState ->
                Image(
                    painter = painterResource(id = curState),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Shake() {
    var isShake by rememberSaveable { mutableStateOf(false) }
    val alpha by animateDpAsState(
        targetValue = if (isShake) 12.dp else 0.dp,
        animationSpec = keyframes {
            durationMillis = 100
            (-12).dp.at(durationMillis/4)
            (12).dp.at(durationMillis/2)
            (-12).dp.at(durationMillis*3/4)
            (12).dp.at(durationMillis)
        }
    ) { run { if(isShake) isShake = false } }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Button(
            onClick = { if(!isShake) isShake = true },
        ) {
            Image(
                painter = painterResource(id = MyImages.Android.image),
                contentDescription = MyImages.Android.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = alpha)
            )
        }
    }
}