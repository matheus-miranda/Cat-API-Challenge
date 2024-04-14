package com.msmlabs.outcoding.catlist

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.msmlabs.outcoding.R
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.presentation.catlist.CatListScreen
import com.msmlabs.outcoding.presentation.catlist.ListUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var listLoading: String
    private lateinit var listImage: String

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            listLoading = getString(R.string.loading)
            listImage = getString(R.string.cat_image)
        }
    }

    @Test
    fun whenScreenIsLoading_showLoadingWheel() {
        composeTestRule.setContent { CatListScreen(uiState = ListUiState.Loading) }

        composeTestRule.onNodeWithContentDescription(listLoading).assertExists()
    }

    @Test
    fun whenScreenLoadsSuccessfully_showContent() {
        composeTestRule.setContent { CatListScreen(uiState = successUiState) }

        composeTestRule.run {
            onNodeWithContentDescription(listImage)
            onNodeWithText("Exotic Shorthair").assertIsDisplayed()
            onNodeWithText("The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.").assertIsDisplayed()
        }
    }

    @Composable
    fun CatListScreen(uiState: ListUiState) {
        CatListScreen(uiState = uiState, onCatClicked = {}, onShowSnackBar = {})
    }
}

private val successUiState = ListUiState.Success(
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
