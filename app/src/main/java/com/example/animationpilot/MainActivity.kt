package com.example.animationpilot

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animationpilot.ui.theme.AnimationPilotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationPilotTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Dissolve()
                }
            }
        }
    }
}

enum class MyImages(val image: Int) {
    Android(R.drawable.robot), Apple(R.drawable.apple)
}

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimationPilotTheme {
        Dissolve()
    }
}