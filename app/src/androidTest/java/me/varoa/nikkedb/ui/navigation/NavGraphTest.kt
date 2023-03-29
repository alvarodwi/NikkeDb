package me.varoa.nikkedb.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavGraphTest {
  @get:Rule(order = 1)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 2)
  val composeTestRule = createAndroidComposeRule<ComposeTestActivity>()

  private lateinit var navController: TestNavHostController
  private val mockWebServer = MockWebServer()


  @Before
  fun setUp() {
    mockWebServer.start(8080)
    ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
    IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

    val mockSuccessResponse = MockResponse()
      .setResponseCode(200)
      .setBody(JsonConverter.readStringFromFile("list_success_response.json"))
    mockWebServer.enqueue(mockSuccessResponse)

    composeTestRule.setContent {
      AppTheme {
        navController = TestNavHostController(LocalContext.current)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        NavGraph(navController = navController)
      }
    }
  }

  @After
  fun tearDown() {
    mockWebServer.shutdown()
    IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
  }

  @Test
  fun A_verifyStartDestination() {
    navController.assertCurrentRouteName(Screen.Home.route)
  }

  @Test
  fun B_navigateToDetail(){
    val mockDetailResponse = MockResponse()
      .setResponseCode(200)
      .setBody(JsonConverter.readStringFromFile("detail_success_response.json"))
    mockWebServer.enqueue(mockDetailResponse)

    composeTestRule.onAllNodesWithContentDescription("Nikke Item")
      .onFirst().performClick()
    navController.assertCurrentRouteName(Screen.Detail.route)
    composeTestRule.onNodeWithText("Detail NIKKE").assertIsDisplayed()
  }

  @Test
  fun C_navigateToFavorite(){
    composeTestRule.onNodeWithContentDescription("Menu Action Favorite")
      .performClick()
    navController.assertCurrentRouteName(Screen.Favorite.route)
    composeTestRule.onNodeWithText("Favorite NIKKE").assertIsDisplayed()
  }

  @Test
  fun D_navigateToAbout(){
    composeTestRule.onNodeWithContentDescription("Menu Action About")
      .performClick()
    navController.assertCurrentRouteName(Screen.About.route)
    composeTestRule.onNodeWithText("About").assertIsDisplayed()
  }

  private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
  }
}