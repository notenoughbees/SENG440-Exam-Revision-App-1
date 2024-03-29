package nz.ac.uclive.dsi61.examappexample

import android.content.Context
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.uclive.dsi61.examappexample.screens.Screens
import nz.ac.uclive.dsi61.examappexample.ui.theme.LightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(context: Context, navController: NavController) {
    // variables to hold the content of the textfields
    var fromText by rememberSaveable { mutableStateOf(getSharedPref(context, "from")) }
    var toText by rememberSaveable { mutableStateOf(getSharedPref(context, "to")) }
    var itemsText by rememberSaveable { mutableStateOf(getSharedPref(context, "items")) }

    Scaffold( //TODO: is red until do @OptIn & @Suppress
        topBar = {
            MyTopAppBar()
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent, // remove default bg colour
                contentPadding = PaddingValues(16.dp), // make btns align w/ the components in the scaffold's main content
                content = {
                    Row( // must be in row in order to use horizontal arrangement
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween // push btns to edges
                    ) {
                        Button(
                            onClick = {
                                // clear the text fields directly
                                fromText = ""
                                toText = ""
                                itemsText = ""
                                // clear the shared preferences
                                setSharedPref(context, "from", "")
                                setSharedPref(context, "to", "")
                                setSharedPref(context, "items", "")
                            }
                        ) {
                            Text(text = "CLEAR")
                        }

                        Button(
                            onClick = {
                                navController.navigate(Screens.Game.route)
                            }
                        ) {
                            Text(text = "NEW GAME")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box( //TODO: IF IN DOUBT, WRAP EVERYTHING IN A BOX
            modifier = Modifier.fillMaxSize().padding(16.dp) // put padding around entire content: outer
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding) // put textfields below topappbar rather than behind
            ) {
                @Composable
                fun MyTextField(label: String, selectedValue: String, modifier: Modifier = Modifier,
                                onValueChange: (String) -> Unit) {
                    val placeholderText = if(label == "") {
                        "Enter items in sorted order, one item per line." } else { "" }

                    TextField(
                        modifier = modifier,
                        label = { Text(text = label) }, //TODO: remember {}s if put a composable
                        placeholder = { Text(text = placeholderText) },
                        value = selectedValue, // (if selVal was mutState, would do .value)
                        onValueChange = onValueChange, // use the given lambda function
                        colors = TextFieldDefaults.textFieldColors(containerColor = LightGreen)
                    ) //TODO: no {} here
                }

                MyTextField("From", fromText, Modifier.align(Alignment.End)) { newValue ->
                    // pass a lambda: solves issue where, if we had had this in the func,
                    // would get error on the assignment line below bc TextField expects
                    // fromText to be var but it gets declared as val.
                    fromText = newValue
                    setSharedPref(context, "from", newValue)
                }
                MyTextField("To", toText, Modifier.align(Alignment.End)) { newValue ->
                    toText = newValue
                    setSharedPref(context, "to", newValue)
                }
                MyTextField("", itemsText, Modifier.fillMaxSize()) { newValue ->
                    itemsText = newValue
                    setSharedPref(context, "items", newValue)
                } // multiline textbox gets diff modifier
            }
        }
    }
}
