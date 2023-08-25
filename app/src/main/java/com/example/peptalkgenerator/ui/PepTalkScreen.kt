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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.data.PepTalkDao
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.data.PhraseDao
import com.example.peptalkgenerator.model.PepTalkScreenViewModel
//import com.example.peptalkgenerator.model.PepTalkScreenViewModelFactory
//import com.example.peptalkgenerator.model.PepTalkUIState
import com.example.peptalkgenerator.ui.components.BottomAppBar
import com.example.peptalkgenerator.ui.components.TopAppBar
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme
import kotlinx.coroutines.flow.Flow

/*
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
 */

@Composable
fun PepTalkCard(
    pepTalk: String,
    modifier: Modifier = Modifier
){
    Card(
    ){
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
    modifier: Modifier = Modifier
){
    //ScreenState(pepTalkUIState = pepTalkViewModel.pepTalkUIState)
    val pepTalkViewModel: PepTalkScreenViewModel = viewModel(factory = PepTalkScreenViewModel.Factory)
    //working
    val pepTalk by pepTalkViewModel.currentTalk.collectAsState(initial = "")

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
                PepTalkCard(pepTalk = pepTalk)
            }
        },
        bottomBar = { BottomAppBar(pepTalk = pepTalk)}
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