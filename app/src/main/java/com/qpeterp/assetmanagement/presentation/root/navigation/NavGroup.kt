package com.qpeterp.assetmanagement.presentation.root.navigation

sealed class NavGroup {
    data object Auth: NavGroup() {
        const val LOGIN = "login"
        const val REGISTER = "register"
    }

    data object Main: NavGroup() {
        const val HOME = "home"
        const val DETAIL = "detail"
    }
}