package com.example.peptalkgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.peptalkgenerator.ui.PepTalkApp
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

//private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PepTalkGeneratorTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                Surface() {
                    PepTalkApp(drawerState,navController,scope)
                }
            }
        }
    }
}

/* old way
@Composable
fun PepTalkApp(modifier: Modifier = Modifier) {
    PepTalkScreen()
}
 */

