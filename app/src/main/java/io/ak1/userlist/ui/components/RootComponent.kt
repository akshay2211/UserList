package io.ak1.userlist.ui.components

import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import io.ak1.userlist.ui.theme.UserListTheme

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */

@Composable
fun RootComposable(window: Window) {
    val isDark = isSystemInDarkThemeCustom()
    UserListTheme(isDark) {
        window.StatusBarConfig(isDark)

        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

        }
    }
}