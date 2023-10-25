package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
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

fun getSharedPref(context: Context, preferenceKey: String): String {
    //TODO SHEET: initialise sharedPrefs only ONE way, using same string each time: here, "MyPrefs"
    // re-initialise it in this fn so we dont have to re-init in each file/screen
    val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return (sharedPrefs.getString(preferenceKey, "<default value>") ?: "<default value>")
}

fun setSharedPref(context: Context, preferenceKey: String, newValue: String) {
    val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    sharedPrefs.edit().putString(preferenceKey, newValue).apply()
}
