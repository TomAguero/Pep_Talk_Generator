package com.example.peptalkgenerator.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.model.PepTalkDetails
import com.example.peptalkgenerator.model.PepTalkDetailsUIState
import com.example.peptalkgenerator.model.PepTalkDetailsViewModel
import com.example.peptalkgenerator.model.toPepTalk
import com.example.peptalkgenerator.ui.navigation.NavigationDestination
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme
import kotlinx.coroutines.launch

object PepTalkDetailsDestination : NavigationDestination {
    override val route = "pepTalk_Details"
    override val titleRes = R.string.pepTalkDetails_screen
    const val pepTalkIdArgs = "pepTalkId"
    val routeWithArgs = "$route/{$pepTalkIdArgs}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkDetailsScreen(
    drawerState: DrawerState,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pepTalkDetailsViewModel: PepTalkDetailsViewModel =
        viewModel(factory = PepTalkDetailsViewModel.Factory)
    val pepTalkDetailsUiState by pepTalkDetailsViewModel.pepTalkDetailsUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(drawerState = drawerState)
        }, modifier = modifier
    ) { innerPadding ->
        PepTalkDetailsBody(
            pepTalkDetailsUIState = pepTalkDetailsUiState,
            onUnFavoriteItem = {
                coroutineScope.launch {
                    pepTalkDetailsViewModel.removeFromFavorites()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Composable
private fun PepTalkDetailsBody(
    pepTalkDetailsUIState: PepTalkDetailsUIState,
    onUnFavoriteItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(4.dp)
    ) {
        var unFavoriteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        PepTalkItem(
            pepTalk = pepTalkDetailsUIState.pepTalkDetails.toPepTalk(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.padding(8.dp))

        //region Share button
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, pepTalkDetailsUIState.pepTalkDetails.toPepTalk().pepTalk)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current

        Button(
            onClick = { context.startActivity(shareIntent) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.button_share),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
        //endregion

        Button(
            onClick = { unFavoriteConfirmationRequired = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.unFavorite),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        if (unFavoriteConfirmationRequired) {
            UnFavoriteConfirmationDialog(
                onUnFavoriteConfirm = {
                    unFavoriteConfirmationRequired = false
                    onUnFavoriteItem()
                },
                onUnFavoriteCancel = { unFavoriteConfirmationRequired = false })
        }

    }
}

@Composable
fun PepTalkItem(
    pepTalk: PepTalk,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = pepTalk.pepTalk,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun UnFavoriteConfirmationDialog(
    onUnFavoriteConfirm: () -> Unit,
    onUnFavoriteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.unFavorite_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onUnFavoriteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onUnFavoriteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}

@Preview(showBackground = true)
@Composable
fun PepTalkDetailsScreenPreview() {
    PepTalkGeneratorTheme {
        PepTalkDetailsBody(
            pepTalkDetailsUIState = (
                    PepTalkDetailsUIState(
                        pepTalkDetails = PepTalkDetails(
                            1,
                            "Champ, the mere idea of you has serious game, 24/7.",
                            favorite = true,
                            block = false
                        )
                    )
                    ),
            onUnFavoriteItem = {}
        )
    }
}