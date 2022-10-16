package com.hyperborge.pablosfitness.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyperborge.pablosfitness.common.TestData
import com.hyperborge.pablosfitness.data.repository.DbRepository
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dbRepository: DbRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbRepository.insertActivities(TestData.weightActivities() + TestData.distanceActivities())
        setContent {
            PablosFitnessTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PablosFitnessTheme {
        Greeting("Android")
    }
}