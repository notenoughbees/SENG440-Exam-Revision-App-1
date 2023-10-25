package nz.ac.uclive.dsi61.examappexample

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import nz.ac.uclive.dsi61.examappexample.screens.Screens

@Composable
fun GameScreen(context: Context, navController: NavController) {
    Surface() {
        Button(
            onClick = {
                navController.navigate(Screens.Setup.route)
            }
        ) { Text(text = "Current: Game") }
    }
}
