package com.qpeterp.assetmanagement.presentation.root.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.qpeterp.assetmanagement.application.MyApplication
import com.qpeterp.assetmanagement.application.PreferenceManager
import com.qpeterp.assetmanagement.presentation.root.navigation.NavigationGraph
import com.qpeterp.assetmanagement.presentation.theme.AssetManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MyApplication.prefs = PreferenceManager(application)
        val prefs = MyApplication.prefs

        val isLoggedIn = prefs.userId.isNotEmpty()

        setContent {
            AssetManagementTheme {
                MyApp(isLoggedIn)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    isUserLoggedIn: Boolean,
) {
    val navController = rememberNavController()
    NavigationGraph(
        navController = navController,
        isUserLoggedIn = isUserLoggedIn
    )
}
