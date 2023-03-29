package me.varoa.nikkedb.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Detail : Screen("detail/{url}") {
        const val args0 = "url"
        fun createRoute(url: String) = "detail/$url"
    }
    object About : Screen("about")
}
