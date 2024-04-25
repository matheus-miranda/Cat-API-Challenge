package com.msmlabs.outcoding.catlist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.msmlabs.outcoding.R
import com.msmlabs.outcoding.catlist.CatListScreenTestHelper.successUiState
import com.msmlabs.outcoding.presentation.catlist.ListUiState
import com.msmlabs.outcoding.stringResource
import org.junit.Rule
import org.junit.Test

class CatListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val listLoading by composeTestRule.stringResource(R.string.loading)
    private val listImage by composeTestRule.stringResource(R.string.cat_image)

    @Test
    fun whenScreenIsLoading_showLoadingWheel() {
        composeTestRule.setContent { CatListScreen(uiState = ListUiState.Loading) }

        composeTestRule.onNodeWithContentDescription(listLoading).assertExists()
    }

    @Test
    fun whenScreenLoadsSuccessfully_showContent() {
        composeTestRule.setContent { CatListScreen(uiState = successUiState) }

        composeTestRule.run {
            onNodeWithContentDescription(listImage).assertExists()
            onNodeWithText("Exotic Shorthair").assertIsDisplayed()
            onNodeWithText("The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.").assertIsDisplayed()
        }
    }
}
