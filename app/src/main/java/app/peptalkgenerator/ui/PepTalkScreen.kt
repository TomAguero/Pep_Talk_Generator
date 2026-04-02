package app.peptalkgenerator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.peptalkgenerator.R
import app.peptalkgenerator.model.PepTalkScreenViewModel
import app.peptalkgenerator.ui.components.BottomAppBar
import app.peptalkgenerator.ui.components.TopAppBar

@Composable
fun PepTalkCard(
    pepTalk: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = pepTalk,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkScreen(
    drawerState: DrawerState,
    navigateToFavorites: () -> Unit
) {
    val pepTalkViewModel: PepTalkScreenViewModel =
        viewModel(factory = PepTalkScreenViewModel.Factory)
    val pepTalk = pepTalkViewModel.talkState
    val uiState = pepTalkViewModel.pepTalkUiState

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopAppBar(drawerState = drawerState) },
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                if (uiState.isFromNotification) {
                    AssistChip(
                        onClick = {},
                        label = { Text(stringResource(R.string.from_morning_pep_talk)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                PepTalkCard(pepTalk = pepTalk)
            }
        },
        bottomBar = {
            BottomAppBar(
                pepTalk = pepTalk,
                snackbarHostState = snackbarHostState,
                navigateToFavorites = navigateToFavorites
            )
        }
    )
}
/*
@Preview(showBackground = true)
@Composable
fun PepTalkAppPreview(modifier: Modifier = Modifier) {
    PepTalkGeneratorTheme(darkTheme = false) {
        PepTalkScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PepTalkAppDarkPreview(modifier: Modifier = Modifier) {
    PepTalkGeneratorTheme(darkTheme = true) {
        PepTalkScreen()
    }
}

 */