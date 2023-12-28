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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.Switch
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState
) {
    var reminderEnabledState by remember { mutableStateOf(true) }

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
}

//region Setting Switch Item Composable
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
            val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled

            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                modifier = Modifier.alpha(contentAlpha)
            )
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.alpha(contentAlpha)
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
        SettingsScreen(drawerState = rememberDrawerState(DrawerValue.Closed))
    }
}