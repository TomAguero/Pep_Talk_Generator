package com.example.peptalkgenerator.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.peptalkgenerator.R
import kotlinx.coroutines.launch

//for future reference on where I got much of this from
// https://tomas-repcik.medium.com/material-3-navigation-drawer-with-android-jetpack-compose-eda9285f9f4c

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: Enum<T>> AppDrawerContent(
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo<T>>,
    defaultPick: T,
    onClick: (T) -> Unit, //Navigate on Click
    modifier: Modifier = Modifier
){
    var currentPick by remember { mutableStateOf(defaultPick) }
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.app_name),
                maxLines = 1,
                style = MaterialTheme.typography.displaySmall
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(8.dp)
            ){
                items(menuItems){ item -> //Adding a list of Screen objects
                    AppDrawerItem(item = item){navOption ->
                        // if it is the same - ignore the click
                        if (currentPick == navOption) {
                            //return@AppDrawerItem
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }

                        currentPick = navOption

                        // close the drawer after clicking the option
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        // navigate to the required screen
                        onClick(navOption)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDrawerItem(
    item: AppDrawerItemInfo<T>,
    onClick: (options: T) -> Unit
) =
    // making surface clickable causes to show the appropriate splash animation
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .width(100.dp),
        onClick = { onClick(item.drawerOption) },
        shape = RoundedCornerShape(50),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = rememberVectorPainter(image = item.icon),
                contentDescription = stringResource(id = item.descriptionId),
                modifier = Modifier
                    .size(36.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
        }
    }