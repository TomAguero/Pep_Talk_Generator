package app.peptalkgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import app.peptalkgenerator.ui.PepTalkApp
import app.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If launched by tapping the morning notification, seed the app with that exact pep talk
        intent.getStringExtra(EXTRA_PEP_TALK)?.let {
            (application as PepTalkApplication).pendingNotificationTalk = it
        }
        setContent {
            PepTalkGeneratorTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val navController = rememberNavController()
                PepTalkApp(drawerState, navController)
            }
        }
    }

    companion object {
        const val EXTRA_PEP_TALK = "extra_pep_talk"
    }
}

