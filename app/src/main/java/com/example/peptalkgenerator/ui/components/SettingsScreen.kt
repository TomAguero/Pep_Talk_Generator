package com.example.peptalkgenerator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.notification.MorningNotificationScheduler
import com.example.peptalkgenerator.notification.NotificationPreferences
import com.example.peptalkgenerator.ui.navigation.NavigationDestination

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopAppBar(drawerState = drawerState) },
        modifier = modifier
    ) { innerPadding ->
        SettingsBody(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun SettingsBody(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var notificationsEnabled by remember {
        mutableStateOf(NotificationPreferences.isEnabled(context))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            stringResource(R.string.settings_notifications_header),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.settings_morning_pep_talk_label),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { enabled ->
                    notificationsEnabled = enabled
                    NotificationPreferences.setEnabled(context, enabled)
                    if (enabled) {
                        MorningNotificationScheduler.schedule(context)
                    } else {
                        MorningNotificationScheduler.cancel(context)
                    }
                }
            )
        }
        Text(
            stringResource(R.string.settings_morning_pep_talk_description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
