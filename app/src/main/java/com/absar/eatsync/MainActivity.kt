package com.absar.eatsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.absar.eatsync.navigation.EatSyncNavGraph
import com.absar.eatsync.ui.theme.EatSyncTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            EatSyncTheme {
                EatSyncNavGraph()
            }
        }
    }
}