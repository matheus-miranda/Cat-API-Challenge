package com.msmlabs.outcoding.presentation.catdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.msmlabs.outcoding.presentation.catdetail.CatDetailRoute

const val CAT_ID = "catId"
const val CAT_DETAIL_ROUTE = "cat_detail/{$CAT_ID}"

fun NavController.navigateToCatDetail(catId: String, navOptions: NavOptions? = null) {
    val route = "cat_detail/$catId"
    navigate(
        route, navOptions
    )
}

fun NavGraphBuilder.catDetailScreen(
    navigateUp: () -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
) {
    composable(
        route = CAT_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(CAT_ID) { type = NavType.StringType }
        )
    ) {
        CatDetailRoute(navigateUp = navigateUp, onShowSnackBar = onShowSnackBar)
    }
}
