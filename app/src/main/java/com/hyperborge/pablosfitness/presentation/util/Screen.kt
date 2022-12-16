package com.hyperborge.pablosfitness.presentation.util

sealed class Screen(
    val route: String
) {
    object WorkoutsScreen : Screen("workouts")
    object WorkoutScreen : Screen("workout")
    object ExercisesScreen : Screen("exercises")
    object ExerciseScreen : Screen("exercise")
}