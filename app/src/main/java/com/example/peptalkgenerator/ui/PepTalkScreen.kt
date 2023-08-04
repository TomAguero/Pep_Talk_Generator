package com.example.peptalkgenerator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.PepTalkViewModel
import com.example.peptalkgenerator.ui.components.BottomAppBar
import com.example.peptalkgenerator.ui.components.TopAppBar
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkScreen(
    pepTalkViewModel: PepTalkViewModel = viewModel()
){
    val phraseUIState by pepTalkViewModel.uiState.collectAsState()
    val pepTalk = phraseUIState.pepTalk

    Scaffold (
        modifier = Modifier,
        topBar = {TopAppBar()},
        content = {paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Card(modifier = Modifier){
                    Text(
                        pepTalk,
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        },
        bottomBar = { BottomAppBar(pepTalk = pepTalk)}
    )
}

@Preview(showBackground = true)
@Composable
fun PepTalkAppPreview(modifier: Modifier = Modifier) {
    PepTalkGeneratorTheme(darkTheme = false) {
        PepTalkScreen(  )
    }
}

@Preview(showBackground = true)
@Composable
fun PepTalkAppDarkPreview(modifier: Modifier = Modifier) {
    PepTalkGeneratorTheme(darkTheme = true) {
        PepTalkScreen(  )
    }
}