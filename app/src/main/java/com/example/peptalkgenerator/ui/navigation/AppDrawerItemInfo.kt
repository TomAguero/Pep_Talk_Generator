package com.example.peptalkgenerator.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.peptalkgenerator.R

enum class MainNavOption {
    Home,
    NewDestination,
    FavoritesDestination
}

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    val icon: ImageVector,
    @StringRes val descriptionId: Int
)

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            MainNavOption.NewDestination,
            R.string.generate_new,
            Icons.Default.Refresh,
            R.string.generate_new
        ),
        AppDrawerItemInfo(
            MainNavOption.FavoritesDestination,
            R.string.favorites_screen,
            Icons.Default.Favorite,
            R.string.favorites_screen
        )
    )
}