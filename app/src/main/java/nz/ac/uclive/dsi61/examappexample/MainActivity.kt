package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import nz.ac.uclive.dsi61.examappexample.ui.theme.ExamAppExampleTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        setContent {
            ExamAppExampleTheme(content = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Sort of Game") },
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding) // put textfield below the topappbar
                    ) {
                        @Composable
                        fun MyTextField() {
                            //TODO: make fn to save/retrieve
                            var selectedFrom by rememberSaveable{mutableStateOf((sharedPreferences.getString("from", "Default Value") ?: "Default Value"))}   //saveable: persist config changes. TODO SHEET: remember mutableStateOf(

                            TextField(
                                value = selectedFrom,
                                onValueChange = { newFrom ->
                                    selectedFrom = newFrom
                                    sharedPreferences.edit().putString("from", newFrom).apply()
                                },
                                label = { Text(text = "From") }
                            ) //TODO SHEET: no {} here
                        }
                        MyTextField()


                    }
                }
            })
        }
    }
}
