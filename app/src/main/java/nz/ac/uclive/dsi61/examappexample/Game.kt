package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.uclive.dsi61.examappexample.ui.theme.Grey
import nz.ac.uclive.dsi61.examappexample.ui.theme.VeryLightGrey

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(context: Context, navController: NavController) {
    //TODO: add topappbar & make a fn for it
    Scaffold(
    ) {
        val items = getSharedPref(context, "items")
//        Log.d("FOO", items)
        val splitItems = items.split("\n")
        //TODO: randomise order
        Log.d("FOO", splitItems.toString())

        MyList(splitItems, {})


    }
}


@Composable
fun MyList(words: List<String>, onItemClick: (String) -> Unit) {
    LazyColumn ( // LazyColumn is Compose equivalent of RecyclerView
    ) { // Only renders the visible elements passed to the items function.
        items(words) { word -> // import ext fn
            // Styling for an individual item in the list
            Row(
                modifier = Modifier
                .background(VeryLightGrey)
            ) {
                Text(
                    modifier = Modifier
                        .padding(all = 48.dp)
                        .clickable { onItemClick(word) },
                    text = word,
                )

                Spacer( // push btns to the right side of the screen
                    modifier = Modifier.weight(1f)
                )

                Column( // !!!!!!!! "put these 2 btns in a col together" !!!!!!!!
                    horizontalAlignment = Alignment.End
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
