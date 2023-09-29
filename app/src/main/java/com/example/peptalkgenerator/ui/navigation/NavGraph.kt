package com.example.peptalkgenerator.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.peptalkgenerator.ui.PepTalkScreen
import com.example.peptalkgenerator.ui.components.FavoritesScreen
import com.example.peptalkgenerator.ui.components.PepTalkDetailsDestination
import com.example.peptalkgenerator.ui.components.PepTalkDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkNavHost(
    drawerState: DrawerState,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainNavOption.NewDestination.name,
        modifier = modifier
    ) {
        composable(MainNavOption.NewDestination.name) {
            PepTalkScreen(
                drawerState,
                navigateToFavorites = {
                    navController.navigate(MainNavOption.FavoritesDestination.name)
                }
            )
        }
        composable(MainNavOption.FavoritesDestination.name) {
            FavoritesScreen(
                drawerState,
                navigateToPepTalkDetails = {
                    navController.navigate("${PepTalkDetailsDestination.route}/${it}")
                }
            )
        }
        composable(
            PepTalkDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(PepTalkDetailsDestination.pepTalkIdArgs) {
                type = NavType.IntType
            })
        ) {
            PepTalkDetailsScreen(
                drawerState,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}