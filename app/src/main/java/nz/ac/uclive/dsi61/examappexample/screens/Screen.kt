package nz.ac.uclive.dsi61.examappexample.screens

sealed class Screens(val route: String) {
    object Setup: Screens("setup_game")
    object Game: Screens("game")
}
