package com.hyperborge.pablosfitness.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hyperborge.pablosfitness.presentation.exercise_screen.ExerciseScreen
import com.hyperborge.pablosfitness.presentation.exercises_screen.ExercisesScreen
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions.NavRouteDefinitionBuilder
import com.hyperborge.pablosfitness.presentation.workout_screen.WorkoutScreen
import com.hyperborge.pablosfitness.presentation.workouts_screen.WorkoutsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PablosFitnessTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph()
                }
            }
        }
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val workoutsScreen = NavRouteDefinitionBuilder.workoutsScreen()
    val workoutScreen = NavRouteDefinitionBuilder.workoutScreen()
    val exercisesScreen = NavRouteDefinitionBuilder.exercisesScreen()
    val exerciseScreen = NavRouteDefinitionBuilder.exerciseScreen()

    NavHost(
        navController = navController,
        startDestination = workoutsScreen.build()
    ) {
        composable(
            route = workoutsScreen.build(),
            arguments = workoutsScreen.arguments
        ) {
            WorkoutsScreen(navController = navController)
        }
        composable(
            route = exercisesScreen.build(),
            arguments = exercisesScreen.arguments
        ) {
            ExercisesScreen(navController = navController)
        }
        composable(
            route = exerciseScreen.build(),
            arguments = exercisesScreen.arguments
        ) {
            ExerciseScreen(navController = navController)
        }
        composable(
            route = workoutScreen.build(),
            arguments = workoutScreen.arguments
        ) {
            WorkoutScreen(navController = navController)
        }
    }
}
