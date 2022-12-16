package com.hyperborge.pablosfitness.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hyperborge.pablosfitness.presentation.exercise_screen.ExerciseScreen
import com.hyperborge.pablosfitness.presentation.exercises_screen.ExercisesScreen
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.NavConstants
import com.hyperborge.pablosfitness.presentation.util.NavRouteBuilder
import com.hyperborge.pablosfitness.presentation.util.Screen
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

    NavHost(
        navController = navController,
        startDestination = NavRouteBuilder.buildRouteDefinition(
            route = Screen.WorkoutsScreen.route,
            queryParams = listOf(NavConstants.PARAM_DATE)
        )
    ) {
        composable(
            route = NavRouteBuilder.buildRouteDefinition(
                route = Screen.WorkoutsScreen.route,
                queryParams = listOf(NavConstants.PARAM_DATE)
            ),
            arguments = listOf(
                navArgument(NavConstants.PARAM_DATE) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            WorkoutsScreen(navController = navController)
        }
        composable(
            route = NavRouteBuilder.buildRouteDefinition(
                route = Screen.ExercisesScreen.route,
                queryParams = listOf(NavConstants.PARAM_DATE)
            ),
            arguments = listOf(
                navArgument(NavConstants.PARAM_DATE) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            ExercisesScreen(navController = navController)
        }
        composable(
            route = NavRouteBuilder.buildRouteDefinition(
                route = Screen.ExerciseScreen.route,
                queryParams = listOf(NavConstants.PARAM_EXERCISE_ID)
            ),
            arguments = listOf(
                navArgument(NavConstants.PARAM_EXERCISE_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            ExerciseScreen(navController = navController)
        }
        composable(
            route = NavRouteBuilder.buildRouteDefinition(
                route = Screen.WorkoutScreen.route,
                queryParams = listOf(
                    NavConstants.PARAM_EXERCISE_ID,
                    NavConstants.PARAM_WORKOUT_ID,
                    NavConstants.PARAM_DATE
                )
            ),
            arguments = listOf(
                navArgument(NavConstants.PARAM_EXERCISE_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(NavConstants.PARAM_WORKOUT_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(NavConstants.PARAM_DATE) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            WorkoutScreen(navController = navController)
        }
    }
}
