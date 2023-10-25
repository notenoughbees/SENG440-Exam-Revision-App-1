package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nz.ac.uclive.dsi61.examappexample.ui.theme.ExamAppExampleTheme
import nz.ac.uclive.dsi61.examappexample.ui.theme.Green
import nz.ac.uclive.dsi61.examappexample.ui.theme.OffWhite

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
                                containerColor = Green, // my colour from Color.kt
                                titleContentColor = Color.White // pre-defined colour
                            )
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            containerColor = Color.Transparent, // remove default bg colour
                            contentPadding = PaddingValues(16.dp), // make btns align w/ the components in the scaffold's main content
                            content = {
                                Row( // must be in row in order to use horizontal arrangement
                                    Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween // push btns to edges
                                ) {
                                    Button(
                                        onClick = {

                                        }
                                    ) {
                                        Text(text = "CLEAR")
                                    }

                                    Button(
                                        onClick = {

                                        }
                                    ) {
                                        Text(text = "NEW GAME")
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box( //TODO SHEET: IF IN DOUBT, WRAP EVERYTHING IN A BOX
                        modifier = Modifier.fillMaxSize().padding(16.dp) // put padding around entire content: outer
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding) // put textfield below topappbar rather than behind
                        ) {
                            @Composable
                            fun MyTextField(label: String, preferenceKey: String, modifier: Modifier = Modifier) {
                                //TODO: make fn to save/retrieve
                                var selectedValue by rememberSaveable{mutableStateOf((sharedPreferences.getString(preferenceKey, "<default value>") ?: "<default value>"))}   //saveable: persist config changes. TODO SHEET: remember mutableStateOf(

                                TextField(
                                    modifier = modifier,
                                    value = selectedValue,
                                    onValueChange = { newValue ->
                                        selectedValue = newValue
                                        sharedPreferences.edit().putString(preferenceKey, newValue).apply()
                                    },
                                    label = { Text(text = label) },
                                    colors = TextFieldDefaults.textFieldColors(containerColor = OffWhite)
                                ) //TODO SHEET: no {} here
                            }

                            MyTextField("From", "from", Modifier.align(Alignment.End))
                            MyTextField("To", "to", Modifier.align(Alignment.End))
                            MyTextField("", "items", Modifier.fillMaxSize()) // multiline textbox gets diff modifier

                        }
                    }
                }
            })
        }
    }
}
