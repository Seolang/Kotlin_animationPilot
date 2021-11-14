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
                    fadeIn(animationSpec = tween(1000, 1000)) with
                            fadeOut(animationSpec = tween(1000))
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
fun Shake() {
    val repTime = 4
    var isShake by rememberSaveable { mutableStateOf(false) }
    val alpha by animateDpAsState(
        // if button clicked, isShake's state is changed so target .dp also changed
        targetValue = if (isShake) 12.dp else 0.dp,
        animationSpec = keyframes {
            durationMillis = 100
            for(item in 1..repTime/2) {
                (-12).dp.at(durationMillis*(2*item-1) / repTime)
                (12).dp.at(durationMillis*(2*item) / repTime)
            }
        }
    // after finish half of animation( which means dp changed 0 to 12 )
    // change isShake state to default so animation start again from 12 to 0
    ) { if(isShake) isShake = false }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Button(
            // Click image change state
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
fun Shake1() {
    var isShake by remember { mutableStateOf(false) }
    val cxt = LocalContext.current
    val alpha by ShakePosition(
        trigger = isShake,
        moveWidth = 12.dp,
        durationTime = 1000,
        repeat = 4
    ) {
        isShake = false
        Toast.makeText(cxt, "핸들러가 작동했습니다.", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        Button(
            // Click image change state
            onClick = {
                //Toast.makeText(cxt, "지금 isShake 는 $isShake 입니다.", Toast.LENGTH_SHORT).show()
                if(!isShake) isShake = true
                      },
        ) {
            Image(
                painter = painterResource(id = MyImages.Android.image),
                contentDescription = MyImages.Android.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = if (isShake) alpha else 0.dp)
            )
        }
    }
}