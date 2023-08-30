package com.example.peptalkgenerator.ui.components
/*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.model.PhraseDetails
import com.example.peptalkgenerator.model.PhraseEntryUIState
import com.example.peptalkgenerator.model.PhraseEntryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhraseEntryScreen(
    modifier: Modifier = Modifier
){
    val phraseEntryViewModel: PhraseEntryViewModel = viewModel(factory = PhraseEntryViewModel.Factory)

    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = { TopAppBar(drawerState = drawerState)}
    ) {innerPadding ->
        PhraseEntryBody(
            phraseEntryUIState = phraseEntryViewModel.phraseEntryUIState,
            onPhraseValueChange = phraseEntryViewModel::updatePhraseUIState,
            onSaveClick = {
                  coroutineScope.launch {
                      phraseEntryViewModel.savePhrase()
                  }
            },
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun PhraseEntryBody(
    phraseEntryUIState: PhraseEntryUIState,
    onPhraseValueChange: (PhraseDetails) -> Unit,
    onSaveClick: (PhraseDetails) -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        //PhraseInputForm
        PhraseInputForm(
            phraseDetails = phraseEntryUIState.phraseDetails,
            onPhraseValueChange = onPhraseValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onSaveClick },
            enabled = phraseEntryUIState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = stringResource(R.string.button_saveCustomPhrase))
        }
    }
}

@Composable
fun PhraseInputForm(
    phraseDetails: PhraseDetails,
    onPhraseValueChange: (PhraseDetails) -> Unit = {},
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ) {
        //Outlined Text Field for Greeting
        //Outlined text field for first half
        //Outlined text field for second half
        //Outlined text field for ending
    }
}

@Preview (showBackground = true)
@Composable
private fun PhraseEntryScreenPreview(){
    PhraseEntryScreen()
}

 */
