package com.example.peptalkgenerator.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.model.PepTalkScreenViewModel
import com.example.peptalkgenerator.model.FavoritesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    drawerState: DrawerState,
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                maxLines = 1,
                style = MaterialTheme.typography.displaySmall
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    // opens drawer
                    drawerState.open()
                }
            }) {
                Icon(
                    // internal hamburger menu
                    Icons.Rounded.Menu,
                    contentDescription = "MenuButton"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    )
}


@Composable
fun BottomAppBar (
    pepTalk: String,
    modifier: Modifier = Modifier
){
    val pepTalkViewModel: PepTalkScreenViewModel = viewModel(factory = PepTalkScreenViewModel.Factory)
    val favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //region get New Button
        Button(onClick = {
            pepTalkViewModel.refreshTalkState()
        }) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    stringResource(R.string.generate_new),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
        //endregion


        //region Favorite button
        Button(
            onClick = {

            }
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    stringResource(R.string.button_favorite),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
        //endregion

        /*
        //region Block button
        Button(
            onClick = { /*TODO*/ }
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    stringResource(R.string.button_block),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    softWrap = false
                )
            }

        }
        //endregion
        */

        //region Share button
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, pepTalk)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current

        Button(onClick = { context.startActivity(shareIntent) }) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    stringResource(R.string.button_share),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
        //endregion
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview(){
    TopAppBar(title = stringResource(R.string.app_name))
}
*/
@Preview(showBackground = true)
@Composable
fun BottomBarPreview(){
    BottomAppBar(
        pepTalk = "Champ, the mere idea of you ha serious game, 24/7.",
        modifier = Modifier
    )
}