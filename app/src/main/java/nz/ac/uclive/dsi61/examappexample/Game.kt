package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.uclive.dsi61.examappexample.screens.Screens
import nz.ac.uclive.dsi61.examappexample.ui.theme.Grey
import nz.ac.uclive.dsi61.examappexample.ui.theme.VeryLightGrey

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(context: Context, navController: NavController) {
    var isCorrectOrderDialogOpen = remember { mutableStateOf(false) }
    var isWrongOrderDialogOpen = remember { mutableStateOf(false) }


    val items = getSharedPref(context, "items")
    val splitItems = items.split("\n") // THE ORIGINAL LIST ORDER: THE TARGET LIST
//    val splitItemsShuffled = splitItems.shuffled()
//    val rememberedMutableSplitItems = remember {
//        mutableStateListOf(*splitItemsShuffled.toTypedArray()) }

    // this block from priscilla c:
    var splitItemsShuffled = mutableListOf<String>("")
    var isShuffled = false
    while (!isShuffled) { // TODO: this prevents items shuffling every time we rotate screen!!!!
        splitItemsShuffled = (splitItems.shuffled() as MutableList<String>?)!!
        if (splitItems != splitItemsShuffled) {
            isShuffled = true
        } }

    //  Must make MyList composable get recomposed when data changes.
    //  To do this, use remember fn & mutableStateOf():
    //  remember the items so that when they change, we recompose & update the UI!
    val rememberedMutableSplitItems by rememberSaveable { mutableStateOf(splitItemsShuffled) } // (don't need mutableState[List]Of(splitItemsShuffled) bc splitItemsShuffled is defined as mutable already)

    Scaffold(
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
                        horizontalArrangement = Arrangement.End // push btn to right edge
                    ) {
                        Button(
                            onClick = {
                                Log.d("LIST1", splitItems.toString())
                                Log.d("LIST2", rememberedMutableSplitItems.toList().toString())
                                if(rememberedMutableSplitItems.toList() == splitItems) { // convert to list so we compare 2 lists!
                                    isCorrectOrderDialogOpen.value = true
                                } else {
                                    isWrongOrderDialogOpen.value = true
                                }
                            }
                        ) {
                            Text(text = "CHECK")
                        }

                        if(isCorrectOrderDialogOpen.value) {
                            AlertDialog(
                                onDismissRequest = { // when press phone back btn or click off the dialog
                                    isCorrectOrderDialogOpen.value = false
                                },
                                title = {},
                                text = { Text(text = "You sorted these correctly. Nice work!") },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            isCorrectOrderDialogOpen.value = false
                                            navController.navigate(Screens.Setup.route)
                                        }
                                    ) {
                                        Text(text = "OKAY")
                                    }
                                }
                            )
                        }
                        if(isWrongOrderDialogOpen.value) {
                            AlertDialog(
                                onDismissRequest = { // when press phone back btn or click off the dialog
                                    isWrongOrderDialogOpen.value = false
                                },
                                title = {},
                                text = { Text(text = "Uh oh. That's not the correct order.") },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            isWrongOrderDialogOpen.value = false
                                        }
                                    ) {
                                        Text(text = "RETRY")
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding -> // will use later so we can push content below the topappbar & above the bottomappbar...
        Box( // wrap our lazycolumn & box inside another box so can put padding around everything...
            modifier = Modifier
                .padding(innerPadding) //TODO: push this content below & above the top & bottom app bars!!!!!!
        ) {
            MyList(rememberedMutableSplitItems, {})


            // separate box for the From & To text, which is aligned to the top & bottom
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = getSharedPref(context, "from"))
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = getSharedPref(context, "to"))
                }
            }
        }
    }
}


@Composable
fun MyList(words: MutableList<String>, onItemClick: (String) -> Unit) {
    LazyColumn ( // LazyColumn: Compose equivalent of RecyclerView
        modifier = Modifier
            .padding(16.dp) // ...then do 16dp around the content as well (but not for the top&bottom text)
    ) { // Only renders the visible elements passed to the items function.
        itemsIndexed(words) { index, word -> // items() or itemsIndexed() if need index
            Row(
                modifier = Modifier.background(VeryLightGrey)
            ) {
                Text(
//                    modifier = Modifier.clickable { onItemClick(word) },  //TODO: makes text clickable
                    text = word,
                )

                // push btns to right side of screen
                Spacer(modifier = Modifier.weight(1f))

                Row( //TODO: !!!!!!!! "put these 2 btns in a row together" !!!!!!!!
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            moveText(words, index, "down")
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Grey
                        ),
                    ) {
                        Icon(Icons.Filled.KeyboardArrowDown, null)
                    }

                    IconButton(
                        onClick = {
                            moveText(words, index, "up")
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Grey
                        ),
                    ) {
                        Icon(Icons.Filled.KeyboardArrowUp, null)
                    }
                }
            }
        }
    }
}

fun moveText(textFields: MutableList<String>, index: Int, direction: String) {
    // must be mutable list so that we can reorganise the texts in the list
    if (direction == "up" && index > 0) { // don't do functionality for the end button
        Log.d("FOO", "$index up")
        val temp = textFields[index]
        Log.d("FOO", temp)
        textFields[index] = textFields[index - 1]
        textFields[index - 1] = temp
    } else if (direction == "down" && index < textFields.size - 1) {
        Log.d("FOO", "$index down")
        val temp = textFields[index]
        Log.d("FOO", temp)
        textFields[index] = textFields[index + 1]
        textFields[index + 1] = temp
    }
}
