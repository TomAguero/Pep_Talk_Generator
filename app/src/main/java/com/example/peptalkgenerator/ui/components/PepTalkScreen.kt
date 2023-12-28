package com.example.peptalkgenerator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.model.PepTalkScreenViewModel

@Composable
fun PepTalkCard(
    pepTalk: String
) {
    Card(
    ) {
        Text(
            text = pepTalk,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkScreen(
    drawerState: DrawerState,
    navigateToFavorites: () -> Unit,
    pepTalkViewModel: PepTalkScreenViewModel = hiltViewModel()
) {
    val pepTalk = pepTalkViewModel.talkState

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                drawerState = drawerState,
                screenTitle = R.string.app_name
            )
        },
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                PepTalkCard(pepTalk = pepTalk)
            }
        },
        bottomBar = {
            BottomAppBar(
                pepTalk = pepTalk,
                pepTalkDetails = pepTalkViewModel.pepTalkUiState.pepTalkDetails,
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