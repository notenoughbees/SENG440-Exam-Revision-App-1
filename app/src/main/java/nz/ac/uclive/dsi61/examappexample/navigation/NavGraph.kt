package nz.ac.uclive.dsi61.examappexample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost //TODO SHEET: incl '.compose'!!
import androidx.navigation.compose.composable
import nz.ac.uclive.dsi61.examappexample.GameScreen
import nz.ac.uclive.dsi61.examappexample.SetupScreen
import nz.ac.uclive.dsi61.examappexample.screens.Screens

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Setup.route
    ) {
        composable(
            route = Screens.Setup.route
        ) { backStackEntry ->
            SetupScreen(LocalContext.current, navController)
        }
        composable(
            route = Screens.Game.route
        ) { backStackEntry ->
            GameScreen(LocalContext.current, navController)
        }
    }
}
