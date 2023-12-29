package com.example.peptalkgenerator.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.model.FavoritesViewModel
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    drawerState: DrawerState,
    navigateToPepTalkDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesUiState by favoritesViewModel.favoritesUiState.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                drawerState = drawerState,
                screenTitle = R.string.screen_favorites
            )
        },
    ) { innerPadding ->
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = stringResource(R.string.favorites_explainer),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
        FavoritesBody(
            favoritesList = favoritesUiState.favoritesList,
            onPepTalkClick = navigateToPepTalkDetails,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun FavoritesBody(
    favoritesList: List<PepTalk>,
    onPepTalkClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (favoritesList.isEmpty()) {
            Text(
                text = stringResource(R.string.noFavorites),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            FavoritesList(
                favoritesList = favoritesList,
                onPepTalkClick = { onPepTalkClick(it.id) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun FavoritesList(
    favoritesList: List<PepTalk>,
    onPepTalkClick: (PepTalk) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = favoritesList, key = { it.id }) { item ->
            FavoriteItem(
                pepTalk = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_Small))
                    .clickable { onPepTalkClick(item) }
            )
        }
    }
}

@Composable
fun FavoriteItem(
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

@Preview(showBackground = true)
@Composable
fun FavoriteBodyPreview() {
    PepTalkGeneratorTheme {
        FavoritesBody(
            listOf(
                PepTalk(
                    id = 1,
                    "Check it: your DNA roars like a lion, snuggle bear.",
                    favorite = true,
                    block = false
                ),
                PepTalk(
                    id = 2,
                    "Man, whatever your secret is gets the party hopping, it is known.",
                    favorite = true,
                    block = false
                ),
                PepTalk(
                    id = 3,
                    "Bro, your life's journey is the next big thing, this is the way",
                    favorite = true,
                    block = false
                )
            ),
            onPepTalkClick = {}
        )
    }
}