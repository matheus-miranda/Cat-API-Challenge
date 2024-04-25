package com.msmlabs.outcoding.catdetail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.msmlabs.outcoding.R
import com.msmlabs.outcoding.catdetail.CatDetailScreenTestHelper.successUiState
import com.msmlabs.outcoding.presentation.catdetail.DetailUiState
import com.msmlabs.outcoding.stringResource
import org.junit.Rule
import org.junit.Test

class CatDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val detailLoading by composeTestRule.stringResource(R.string.loading)
    private val detailImage by composeTestRule.stringResource(R.string.cat_image)

    @Test
    fun whenScreenIsLoading_showLoadingWheel() {
        composeTestRule.setContent { CatDetailScreen(uiState = DetailUiState.Loading) }

        composeTestRule.onNodeWithContentDescription(detailLoading).assertExists()
    }

    @Test
    fun whenScreenLoadsSuccessfully_showContent() {
        composeTestRule.setContent { CatDetailScreen(uiState = successUiState) }

        composeTestRule.run {
            onNodeWithContentDescription(detailImage).assertExists()
            onNodeWithText("Exotic Shorthair").assertIsDisplayed()
            onNodeWithText("The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.").assertIsDisplayed()
            onNodeWithText("Origin: United States").assertIsDisplayed()
            onNodeWithText("Life Span: 12 - 15").assertIsDisplayed()
            onNodeWithText("Characteristics: Affectionate, Sweet, Loyal, Quiet, Peaceful").assertIsDisplayed()
            onNodeWithText("http://en.wikipedia.org").assertIsDisplayed()
        }
    }
}
