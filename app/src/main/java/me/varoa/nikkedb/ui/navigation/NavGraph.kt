package me.varoa.nikkedb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.varoa.nikkedb.ui.screen.about.AboutScreen
import me.varoa.nikkedb.ui.screen.detail.DetailScreen
import me.varoa.nikkedb.ui.screen.detail.DetailViewModel
import me.varoa.nikkedb.ui.screen.favorite.FavoriteScreen
import me.varoa.nikkedb.ui.screen.favorite.FavoriteViewModel
import me.varoa.nikkedb.ui.screen.home.HomeScreen
import me.varoa.nikkedb.ui.screen.home.HomeViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Home
        composable(
            route = Screen.Home.route
        ) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navigateToDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                },
                navigateToFavorite = {
                    navController.navigate(Screen.Favorite.route)
                },
                navigateToAbout = {
                    navController.navigate(Screen.About.route)
                }
            )
        }
        composable(
            route = Screen.Favorite.route
        ) {
            val viewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.Detail.args0) { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<DetailViewModel>()
            DetailScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.About.route
        ) {
            AboutScreen(
                navigateBack = navController::navigateUp
            )
        }
    }
}
