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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    //TODO: add topappbar & make a fn for it
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
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End // push btn to right edge
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screens.Setup.route) //TODO
                            }
                        ) {
                            Text(text = "CHECK")
                        }
                    }
                }
            )
        }
    ) { innerPadding -> // will use later so we can push content below the topappbar & above the bottomappbar...
        val items = getSharedPref(context, "items")
//        Log.d("FOO", items)
        val splitItems = items.split("\n")
        //TODO: randomise order
        Log.d("FOO", splitItems.toString())

        Box( // wrap our lazycolumn & box inside another box so can put padding around everything...
            modifier = Modifier
                .padding(innerPadding) //TODO SHEET: push this content below & above the top & bottom app bars!!!!!!
        ) {
            MyList(splitItems, {})


            // separate box for the From & To text, which is aligned to the top & bottom
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = getSharedPref(context, "from")
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getSharedPref(context, "to")
                    )
                }
            }
        }
    }
}


@Composable
fun MyList(words: List<String>, onItemClick: (String) -> Unit) {
    LazyColumn ( // LazyColumn is Compose equivalent of RecyclerView
        modifier = Modifier
            .padding(16.dp) // ...then do 16dp around the content as well (but not for the top&bottom text)
    ) { // Only renders the visible elements passed to the items function.
        items(words) { word -> // import ext fn
            // Styling for an individual item in the list
            Row(
                modifier = Modifier
                .background(VeryLightGrey)
            ) {
                Text(
                    modifier = Modifier,
//                        .padding(all = 48.dp),
//                        .clickable { onItemClick(word) },     // TODO SHEET: makes text clickable
                    text = word,
                )

                Spacer( // push btns to right side of screen
                    modifier = Modifier.weight(1f)
                )

                Row( // !!!!!!!! "put these 2 btns in a col together" !!!!!!!!
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Grey
                        ),
                    ) {
                        Icon(Icons.Filled.KeyboardArrowUp, null)
                    }

                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Grey
                        ),
                    ) {
                        Icon(Icons.Filled.KeyboardArrowDown, null)
                    }
                }
            }
        }
    }
}
