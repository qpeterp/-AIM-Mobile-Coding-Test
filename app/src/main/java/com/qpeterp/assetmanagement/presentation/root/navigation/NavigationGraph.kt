package com.qpeterp.assetmanagement.presentation.root.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qpeterp.assetmanagement.presentation.features.auth.register.screen.RegisterScreen
import com.qpeterp.assetmanagement.presentation.features.home.screen.HomeScreen

@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn) NavGroup.Main.HOME else NavGroup.Auth.REGISTER,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },   // ⬅ 나가는 애니메이션 제거
        popEnterTransition = { EnterTransition.None }, // ⬅ 뒤로 왔을 때 애니메이션 제거
        popExitTransition = { ExitTransition.None }   // ⬅ 뒤로 나갈 때 애니메이션 제거
    ) {
        // Auth 그룹 네비게이션
        composable(NavGroup.Auth.REGISTER) { RegisterScreen(navController) }

        // Main 그룹 네비게이션
        composable(NavGroup.Main.HOME) { HomeScreen(navController) }
//        composable(NavGroup.Main.DETAIL) { SecurityScreen(navController) }
    }
}