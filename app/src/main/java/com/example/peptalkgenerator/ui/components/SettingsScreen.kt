package com.example.peptalkgenerator.ui.components

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme
import com.example.peptalkgenerator.workmanager.NotificationViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


/*
for future reference on how I did this
https://alexzh.com/jetpack-compose-switch/#testing-the-settings-screen
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState
) {
    val notificationViewModel: NotificationViewModel =
        viewModel(factory = NotificationViewModel.Factory)

    val uiState = notificationViewModel.uiState.collectAsState().value

    var reminderEnabledState = uiState.reminderEnabledState

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                drawerState = drawerState,
                screenTitle = R.string.screen_settings
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            SettingSwitchItem(
                title = R.string.settings_reminder_title,
                description = R.string.settings_reminder_desc,
                checked = reminderEnabledState,
                onCheckedChange = { reminderEnabledState = it }
            )
        }
    }

    if (reminderEnabledState) {
        RequestNotificationPerms()
        notificationViewModel.scheduleNotification(reminderEnabledState = true)
    } else {
        notificationViewModel.cancelNotification(reminderEnabledState = false)
    }

}

//region Setting Switch Item composable
@Composable
private fun SettingSwitchItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    @StringRes title: Int,
    @StringRes description: Int,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onCheckedChange
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier.weight(1.0f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (enabled) {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            }

            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled
        )
    }
}
//endregion

// Documentation for this https://google.github.io/accompanist/permissions/
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestNotificationPerms() {

    val notificationPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)


    val showDialog = remember { mutableStateOf(false) }

    if (notificationPermissionState.status.isGranted) {
        Log.d("PepTalkNotification", "Perms already granted")
    } else {
        showDialog.value = true
    }

    val textToShow = if (notificationPermissionState.status.shouldShowRationale) {
        "This permission is needed for daily pep talks."
    } else {
        "Notification permission required for the daily pep talk to happen. " +
                "Please grant this permission."
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Permissions needed.") },
            text = { Text(textToShow) },
            confirmButton = {
                Button(onClick = {
                    notificationPermissionState.launchPermissionRequest()
                    showDialog.value = false
                }) {
                    Text("Request permission")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel".uppercase())
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SettingsPreview() {
    PepTalkGeneratorTheme {
        SettingsScreen(
            drawerState = rememberDrawerState(DrawerValue.Closed)
        )
    }
}