package me.varoa.nikkedb.ui.navigation

sealed class Screen(val route: String) {
  object Home: Screen("home")
  object Favorite: Screen("favorite")
  object Detail: Screen("detail/{name}"){
    const val args0 = "name"
    fun createRoute(name : String) = "detail/$name"
  }
  object About: Screen("about")
}