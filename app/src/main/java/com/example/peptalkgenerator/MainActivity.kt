package com.example.peptalkgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.peptalkgenerator.ui.PepTalkScreen
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

//private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PepTalkGeneratorTheme {
                Surface() {
                    PepTalkApp()
                }
            }
        }
    }
}

@Composable
fun PepTalkApp(modifier: Modifier = Modifier) {
    PepTalkScreen()
}