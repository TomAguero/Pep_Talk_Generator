package com.example.peptalkgenerator.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.peptalkgenerator.ui.navigation.AppDrawerContent
import com.example.peptalkgenerator.ui.navigation.DrawerParams
import com.example.peptalkgenerator.ui.navigation.MainNavOption
import com.example.peptalkgenerator.ui.navigation.PepTalkNavHost
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkApp(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope
){
    Surface(){
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawerContent(
                    drawerState = drawerState,
                    menuItems = DrawerParams.drawerButtons,
                    defaultPick = MainNavOption.NewDestination,
                    onClick = {onUserPickedOption ->
                        when (onUserPickedOption) {
                            MainNavOption.NewDestination -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(MainNavOption.NewDestination.name)
                                }
                            }
                            MainNavOption.FavoritesDestination -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(MainNavOption.FavoritesDestination.name)
                                }
                            }
                        }
                    }
                )
            }
        ) {
            PepTalkNavHost(
                drawerState = drawerState,
                navController = navController
            )
        }
    }
}