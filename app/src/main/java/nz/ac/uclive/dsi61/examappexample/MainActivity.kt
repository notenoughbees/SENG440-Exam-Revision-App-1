package nz.ac.uclive.dsi61.examappexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    Box(
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
                                    value = selectedValue,
                                    onValueChange = { newValue ->
                                        selectedValue = newValue
                                        sharedPreferences.edit().putString(preferenceKey, newValue).apply()
                                    },
                                    label = { Text(text = label) },
                                    modifier = modifier
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
