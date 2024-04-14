package com.msmlabs.outcoding.catdetail

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.msmlabs.outcoding.R
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.presentation.catdetail.CatDetailScreen
import com.msmlabs.outcoding.presentation.catdetail.DetailUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var detailLoading: String
    private lateinit var detailImage: String

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            detailLoading = getString(R.string.loading)
            detailImage = getString(R.string.cat_image)
        }
    }

    @Test
    fun whenScreenIsLoading_showLoadingWheel() {
        composeTestRule.setContent { CatDetailScreen(uiState = DetailUiState.Loading) }

        composeTestRule.onNodeWithContentDescription(detailLoading).assertExists()
    }

    @Test
    fun whenScreenLoadsSuccessfully_showContent() {
        composeTestRule.setContent { CatDetailScreen(uiState = successUiState) }

        composeTestRule.run {
            onNodeWithContentDescription(detailImage)
            onNodeWithText("Exotic Shorthair").assertIsDisplayed()
            onNodeWithText("The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.").assertIsDisplayed()
            onNodeWithText("Origin: United States").assertIsDisplayed()
            onNodeWithText("Life Span: 12 - 15").assertIsDisplayed()
            onNodeWithText("Characteristics: Affectionate, Sweet, Loyal, Quiet, Peaceful").assertIsDisplayed()
            onNodeWithText("http://en.wikipedia.org").assertIsDisplayed()
        }
    }

    @Composable
    fun CatDetailScreen(uiState: DetailUiState) {
        CatDetailScreen(
            uiState = uiState,
            navigateUp = {}) {
        }
    }
}

private val successUiState = DetailUiState.Success(
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
