package io.ak1.userlist.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
fun RootComposable(liveViewModel: UserViewModel) {

    val isSystemInDarkTheme = isSystemInDarkThemeCustom()
    UserListTheme(isSystemInDarkTheme) {
        var statusBarColor: Color by remember {
            mutableStateOf(Color.Transparent)
        }
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(
                    statusBarColor,
                    darkIcons = darkIcons
                )
            }
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
}