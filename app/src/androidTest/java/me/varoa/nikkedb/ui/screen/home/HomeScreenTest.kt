package me.varoa.nikkedb.ui.screen.home

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import me.varoa.nikkedb.ComposeTestActivity
import me.varoa.nikkedb.core.data.remote.api.ApiConfig
import me.varoa.nikkedb.ui.theme.AppTheme
import me.varoa.nikkedb.utils.EspressoIdlingResource
import me.varoa.nikkedb.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<ComposeTestActivity>()

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        // why setContent not placed here?
        // well because the viewModel on init would fetch
        // and the mock response is different on each test case...
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun A_loadSuccessful() {
        val mockSuccessResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_success_response.json"))
        mockWebServer.enqueue(mockSuccessResponse)

        composeTestRule.setContent {
            AppTheme {
                HomeScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetail = {},
                    navigateToAbout = {},
                    navigateToFavorite = {}
                )
            }
        }

        // assert that the bottom appbar exist
        composeTestRule.onNodeWithContentDescription("Bottom Appbar")
            .assertExists()
        // assert that the search bar exist
        composeTestRule.onNodeWithContentDescription("Search Bar")
            .assertExists()
        // assert that the nikke list is showing
        composeTestRule.onNodeWithContentDescription("List Nikke")
            .assertExists()
        // assert that at least one nikke item is showing
        composeTestRule.onAllNodesWithContentDescription("Nikke Item")
            .onFirst().assertExists()
    }

    @Test
    fun B_loadEmptyData() {
        val mockEmptyResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("list_empty_response.json"))
        mockWebServer.enqueue(mockEmptyResponse)

        composeTestRule.setContent {
            AppTheme {
                HomeScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetail = {},
                    navigateToAbout = {},
                    navigateToFavorite = {}
                )
            }
        }

        // assert that the bottom appbar exist
        composeTestRule.onNodeWithContentDescription("Bottom Appbar")
            .assertExists()
        // assert that the search bar exist
        composeTestRule.onNodeWithContentDescription("Search Bar")
            .assertExists()
        // assert that the empty layout is showing properly
        composeTestRule.onNodeWithContentDescription("Error Layout")
            .assertExists()
        composeTestRule.onNodeWithContentDescription("Error Message")
            .assertTextContains("There is nothing here")
        // assert that retry button is not visible
        composeTestRule.onNodeWithContentDescription("Retry Button")
            .assertDoesNotExist()
    }

    @Test
    fun C_loadFailed() {
        val mockErrorResponse = MockResponse()
            .setResponseCode(404)
            .setBody("Not Found")
        mockWebServer.enqueue(mockErrorResponse)

        composeTestRule.setContent {
            AppTheme {
                HomeScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetail = {},
                    navigateToAbout = {},
                    navigateToFavorite = {}
                )
            }
        }

        // assert that the bottom appbar exist
        composeTestRule.onNodeWithContentDescription("Bottom Appbar")
            .assertExists()
        // assert that the empty layout is showing properly
        composeTestRule.onNodeWithContentDescription("Error Layout")
            .assertExists()
        composeTestRule.onNodeWithContentDescription("Error Message")
            .assertTextContains("404", substring = true)
        // assert that retry button is visible
        composeTestRule.onNodeWithContentDescription("Retry Button")
            .assertExists()
    }
}
