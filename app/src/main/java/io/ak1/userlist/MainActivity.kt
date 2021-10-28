package io.ak1.userlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.ak1.userlist.ui.components.RootComposable
import io.ak1.userlist.ui.screens.UserViewModel
import io.ak1.userlist.ui.theme.UserListTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val liveViewModel by inject<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RootComposable(liveViewModel = liveViewModel, window = window)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UserListTheme {
        Greeting("Android")
    }
}