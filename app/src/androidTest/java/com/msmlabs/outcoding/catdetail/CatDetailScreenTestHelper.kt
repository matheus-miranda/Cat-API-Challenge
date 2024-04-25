package com.msmlabs.outcoding.catdetail

import androidx.compose.runtime.Composable
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.presentation.catdetail.DetailUiState

object CatDetailScreenTestHelper {
    val successUiState = DetailUiState.Success(
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
                    wikipediaUrl = "http://en.wikipedia.org"
                )
            )
        )
    )
}


@Composable
fun CatDetailScreen(uiState: DetailUiState) {
    com.msmlabs.outcoding.presentation.catdetail.CatDetailScreen(
        uiState = uiState,
        navigateUp = {},
        onShowSnackBar = {}
    )
}
