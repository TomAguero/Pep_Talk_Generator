package app.peptalkgenerator

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import app.peptalkgenerator.ui.PepTalkApp
import app.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

class MainActivity : ComponentActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { /* no-op */ }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermissionIfNeeded()
        // If launched by tapping the morning notification, seed the app with that exact pep talk
        intent.getStringExtra(EXTRA_PEP_TALK)?.let {
            (application as PepTalkApplication).pendingNotificationTalk = it
        }
        setContent {
            PepTalkGeneratorTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val navController = rememberNavController()
                Surface() {
                    PepTalkApp(drawerState, navController)
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    companion object {
        const val EXTRA_PEP_TALK = "extra_pep_talk"
    }
}

