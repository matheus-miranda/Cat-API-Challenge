package com.msmlabs.outcoding.catlist

import androidx.compose.runtime.Composable
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.presentation.catlist.ListUiState

object CatListScreenTestHelper {
    val successUiState = ListUiState.Success(
        listOf(
            Cat(
                id = "dVujvBqnu",
                imageUrl = "https://cdn2.thecatapi.com/images/dVujvBqnu.jpg",
                breeds = listOf(
                    Breed(
                        name = "Exotic Shorthair",
                        description = "The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.",
                        temperament = "Affectionate, Sweet, Loyal, Quiet, Peaceful",
                        vcaHospitalsUrl = null,
                        origin = "United States",
                        lifeSpan = "12 - 15",
                        wikipediaUrl = null
                    )
                )
            )
        )
    )
}

@Composable
fun CatListScreen(uiState: ListUiState) {
    com.msmlabs.outcoding.presentation.catlist.CatListScreen(
        uiState = uiState,
        onCatClicked = {},
        onShowSnackBar = {})
}
