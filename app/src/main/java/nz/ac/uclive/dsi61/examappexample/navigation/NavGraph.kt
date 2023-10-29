package nz.ac.uclive.dsi61.examappexample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost //TODO: incl '.compose'!!
import androidx.navigation.compose.composable
import nz.ac.uclive.dsi61.examappexample.GameScreen
import nz.ac.uclive.dsi61.examappexample.SetupScreen
import nz.ac.uclive.dsi61.examappexample.screens.Screens

@Composable //TODO: remmbr to make COMPOSABLE func!!!!
//TODO: function, NOT class!!!!
fun NavGraph(navController: NavHostController) { //TODO: nhc, NOT just nc!!!!
    NavHost(
        navController = navController,
        startDestination = Screens.Setup.route
    ) {
        composable( //TODO: "import androidx.navigation.compose.composable"
                    //    & "import androidx.navigation.compose.NavHost" <- REMMBR: incl 'compose'!!!!
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
