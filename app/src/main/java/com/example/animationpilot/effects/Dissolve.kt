package com.example.animationpilot.effects

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
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
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                )
            }
        }

    }
}