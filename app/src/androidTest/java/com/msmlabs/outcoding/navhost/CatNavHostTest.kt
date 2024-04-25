package com.msmlabs.outcoding.navhost

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.msmlabs.outcoding.HiltComponentActivity
import com.msmlabs.outcoding.catdetail.CatDetailScreen
import com.msmlabs.outcoding.catdetail.CatDetailScreenTestHelper
import com.msmlabs.outcoding.catlist.CatListScreen
import com.msmlabs.outcoding.catlist.CatListScreenTestHelper
import com.msmlabs.outcoding.presentation.navigation.CatNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CatNavHostTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private lateinit var navController: TestNavHostController


    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CatNavHost(onShowSnackBar = {}, navController = navController)
            CatListScreen(uiState = CatListScreenTestHelper.successUiState)
        }

        composeTestRule
            .onNodeWithContentDescription("CardView")
            .assertIsDisplayed()
    }

    @Test
    fun navHost_navigateToDetailScreen() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CatNavHost(onShowSnackBar = {}, navController = navController)
            CatListScreen(uiState = CatListScreenTestHelper.successUiState)
            CatDetailScreen(uiState = CatDetailScreenTestHelper.successUiState)
        }

        composeTestRule.onNodeWithContentDescription("CardView").performClick()
        composeTestRule.onNodeWithText("Details").assertIsDisplayed()
    }

    @Test
    fun navHost_navigateBackToList() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CatNavHost(onShowSnackBar = {}, navController = navController)
            CatListScreen(uiState = CatListScreenTestHelper.successUiState)
            CatDetailScreen(uiState = CatDetailScreenTestHelper.successUiState)
        }

        composeTestRule.onNodeWithContentDescription("CardView").performClick()
        composeTestRule.onNodeWithText("Details").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithContentDescription("CardView").assertIsDisplayed()
    }
}
