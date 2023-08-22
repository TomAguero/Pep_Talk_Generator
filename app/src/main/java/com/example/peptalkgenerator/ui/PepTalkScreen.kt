package com.example.peptalkgenerator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.model.PepTalkScreenViewModel
import com.example.peptalkgenerator.model.PepTalkUIState
import com.example.peptalkgenerator.ui.components.TopAppBar
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

@Composable
fun ScreenState(
    pepTalkUIState: PepTalkUIState,
    modifier: Modifier = Modifier
){
    when (pepTalkUIState) {
        is PepTalkUIState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PepTalkUIState.PepTalk -> PepTalkCard(pepTalkUIState.pepTalk)
        is PepTalkUIState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PepTalkCard(
    pepTalk: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxSize()
    ){
        Text(
            text = pepTalk,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkScreen(

    modifier: Modifier = Modifier
){
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
                val pepTalkViewModel: PepTalkScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
                ScreenState(pepTalkUIState = pepTalkViewModel.pepTalkUIState)
            }
        },
        //bottomBar = { BottomAppBar(pepTalk = pepTalk)}
    )
}

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