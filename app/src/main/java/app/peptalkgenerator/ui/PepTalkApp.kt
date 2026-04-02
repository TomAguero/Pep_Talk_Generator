package app.peptalkgenerator.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.peptalkgenerator.ui.navigation.AppDrawerContent
import app.peptalkgenerator.ui.navigation.DrawerParams
import app.peptalkgenerator.ui.navigation.MainNavOption
import app.peptalkgenerator.ui.navigation.PepTalkNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PepTalkApp(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    navController: NavHostController = rememberNavController()
) {
    Surface() {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawerContent(
                    drawerState = drawerState,
                    menuItems = DrawerParams.drawerButtons,
                    defaultPick = MainNavOption.Home,
                    onClick = { onUserPickedOption ->
                        when (onUserPickedOption) {
                            MainNavOption.Home -> {
                                navController.navigate(MainNavOption.NewDestination.name) {
                                    popUpTo(MainNavOption.NewDestination.name)
                                }
                            }

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

                            MainNavOption.ManageSayingsDestination -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(MainNavOption.ManageSayingsDestination.name)
                                }
                            }

                            MainNavOption.SettingsDestination -> {
                                navController.navigate(onUserPickedOption.name) {
                                    popUpTo(MainNavOption.SettingsDestination.name)
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
