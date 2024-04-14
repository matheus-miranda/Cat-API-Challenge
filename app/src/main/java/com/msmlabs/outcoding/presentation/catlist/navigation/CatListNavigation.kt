package com.msmlabs.outcoding.presentation.catlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msmlabs.outcoding.presentation.catlist.CatListRoute

const val CAT_LIST_ROUTE = "cat_list"

fun NavGraphBuilder.catListScreen(
    onCatClick: (String) -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
) {
    composable(route = CAT_LIST_ROUTE) {
        CatListRoute(onCatClick = onCatClick, onShowSnackBar = onShowSnackBar)
    }
}
