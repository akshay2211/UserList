package io.ak1.userlist.ui.components

import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ak1.userlist.ui.screens.Destinations
import io.ak1.userlist.ui.screens.HomeScreenComposable
import io.ak1.userlist.ui.screens.SettingsScreen
import io.ak1.userlist.ui.screens.UserViewModel
import io.ak1.userlist.ui.theme.UserListTheme

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */
@Composable
fun RootComposable(liveViewModel: UserViewModel, window: Window) {
    val isDark = isSystemInDarkThemeCustom()

    UserListTheme(isDark) {
        window.StatusBarConfig(isDark)

        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Destinations.HOME_ROUTE
            ) {
                composable(Destinations.HOME_ROUTE) {
                    HomeScreenComposable(liveViewModel, navController)
                }
                composable(Destinations.SETTINGS_ROUTE) {
                    SettingsScreen(navController)
                }
            }
        }
    }
}