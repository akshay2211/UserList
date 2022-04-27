package io.ak1.userlist.ui.components

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */
/**
 * extension [isDarkThemeOn] checks the saved theme from preference
 * and returns boolean
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val themePreferenceKey = intPreferencesKey("list_theme")

fun Context.isDarkThemeOn() = dataStore.data
    .map { preferences ->
        // No type safety.
        preferences[themePreferenceKey] ?: 0
    }

@Composable
fun isSystemInDarkThemeCustom(): Boolean {
    val context = LocalContext.current
    val prefs = runBlocking { context.dataStore.data.first() }
    return when (context.isDarkThemeOn()
        .collectAsState(initial = prefs[themePreferenceKey] ?: 0).value) {
        2 -> true
        1 -> false
        else -> context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}