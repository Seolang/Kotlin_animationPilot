package com.example.animationpilot.effects

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.KeyFrames
import com.example.animationpilot.enum.MyImages

@Composable
fun Dissolve() {
    // State of which image loaded
    var state by rememberSaveable { mutableStateOf(MyImages.Android) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        // Make buttons
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
        // Crossfade print images with dissolve effect
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

        Button( // If clicked, change image state
            onClick = {
                if (state == MyImages.Android.image)
                    state = MyImages.Apple.image
                else
                    state = MyImages.Android.image
            }
        ) {
            AnimatedContent( // If state changed, start animation of Current state, then start Initial state
                            // But by giving delays, you can run Initial state animation first.
                targetState = state,
                transitionSpec = {
                    fadeIn(animationSpec = tween(3000, 0)) with
                            fadeOut(animationSpec = tween(3000))
                }
            ) { curState -> // Images which animation is running
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
fun ShakePosition(trigger: Boolean, moveWidth: Dp, durationTime: Int, repeat: Int,
                  finishedListener: ((Dp) -> Unit)? = null): State<Dp> {
    // to change alpha along with isShake, isShake must saved in composition, by remember
    val cxt = LocalContext.current
    val alpha = animateDpAsState(
        targetValue = if (trigger) moveWidth else 0.dp,
        animationSpec = keyframes {
            durationMillis = durationTime
            for(item in 1..repeat) {
                (-moveWidth).at(durationMillis*(2*item-1) / repeat)
                moveWidth.at(durationMillis*(2*item) / repeat)
            }
        },
        finishedListener
    )
    return alpha
}

@ExperimentalAnimationApi
@Composable
fun Shake() {
    var isShake by remember { mutableStateOf(false) }
    val alpha by ShakePosition(
        trigger = isShake,
        moveWidth = 12.dp,
        durationTime = 300,
        repeat = 5
    ) {
        isShake = false
    }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        Button(
            // Click image change state
            onClick = {
                if(!isShake) isShake = true
                      },
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