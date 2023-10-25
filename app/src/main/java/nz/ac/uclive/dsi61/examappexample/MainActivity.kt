package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import nz.ac.uclive.dsi61.examappexample.navigation.NavGraph
import nz.ac.uclive.dsi61.examappexample.ui.theme.ExamAppExampleTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExamAppExampleTheme(content = {
                Scaffold {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            })
        }
    }
}

fun getSharedPref(sharedPreferences: SharedPreferences, preferenceKey: String): String {
    return (sharedPreferences.getString(preferenceKey, "<default value>") ?: "<default value>")
}

fun setSharedPref(sharedPreferences: SharedPreferences, preferenceKey: String, newValue: String) {
    sharedPreferences.edit().putString(preferenceKey, newValue).apply()
}
