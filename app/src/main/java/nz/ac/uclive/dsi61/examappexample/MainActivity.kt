package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
                // variables to hold the content of the textfields
                var fromText by rememberSaveable { mutableStateOf(getSharedPref(sharedPreferences, "from")) }
                var toText by rememberSaveable { mutableStateOf(getSharedPref(sharedPreferences, "to")) }
                var itemsText by rememberSaveable { mutableStateOf(getSharedPref(sharedPreferences, "items")) }

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
                                            // clear the text fields directly
                                            fromText = ""
                                            toText = ""
                                            itemsText = ""
                                            // clear the shared preferences
                                            setSharedPref(sharedPreferences, "from", "")
                                            setSharedPref(sharedPreferences, "to", "")
                                            setSharedPref(sharedPreferences, "items", "")
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
                            fun MyTextField(label: String, selectedValue: String, modifier: Modifier = Modifier,
                                            onValueChange: (String) -> Unit) {
                                TextField(
                                    modifier = modifier,
                                    label = { Text(text = label) },
                                    placeholder = { Text(text = "Enter items in sorted order, one item per line.")}, //TODO: only have this for the multiline textbox
                                    value = selectedValue, // (if selVal was mutState, would do .value)
                                    onValueChange = onValueChange, // use the given lambda function
                                    colors = TextFieldDefaults.textFieldColors(containerColor = OffWhite)
                                ) //TODO SHEET: no {} here
                            }

                            MyTextField("From", fromText, Modifier.align(Alignment.End)) { newValue ->
                                // pass a lambda: solves issue where, if we had had this in the func,
                                // would get error on the assignment line below bc TextField expects
                                // fromText to be var but it gets declared as val.
                                fromText = newValue
                                setSharedPref(sharedPreferences, "from", newValue)
                            }
                            MyTextField("To", toText, Modifier.align(Alignment.End)) { newValue ->
                                toText = newValue
                                setSharedPref(sharedPreferences, "to", newValue)
                            }
                            MyTextField("", itemsText, Modifier.fillMaxSize()) { newValue ->
                                itemsText = newValue
                                setSharedPref(sharedPreferences, "items", newValue)
                            } // multiline textbox gets diff modifier
                        }
                    }
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
