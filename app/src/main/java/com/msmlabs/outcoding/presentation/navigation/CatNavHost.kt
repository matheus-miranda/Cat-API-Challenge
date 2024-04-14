package com.msmlabs.outcoding.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.msmlabs.outcoding.presentation.catdetail.navigation.catDetailScreen
import com.msmlabs.outcoding.presentation.catdetail.navigation.navigateToCatDetail
import com.msmlabs.outcoding.presentation.catlist.navigation.CAT_LIST_ROUTE
import com.msmlabs.outcoding.presentation.catlist.navigation.catListScreen

@Composable
fun CatNavHost(
    onShowSnackBar: suspend (String) -> Unit,
    startDestination: String = CAT_LIST_ROUTE,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        catListScreen(
            onShowSnackBar = onShowSnackBar,
            onCatClick = navController::navigateToCatDetail
        )
        catDetailScreen(
            onShowSnackBar = onShowSnackBar,
            navigateUp = navController::popBackStack,
        )
    }
}
