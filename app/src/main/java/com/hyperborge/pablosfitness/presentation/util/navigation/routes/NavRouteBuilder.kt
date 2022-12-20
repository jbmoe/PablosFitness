package com.hyperborge.pablosfitness.presentation.util.navigation.routes

import java.time.OffsetDateTime

object NavRouteBuilder {
    fun workoutsScreen(date: OffsetDateTime): WorkoutsRouteBuilder = WorkoutsRouteBuilder(date)
    fun workoutScreen(date: OffsetDateTime): WorkoutRouteBuilder = WorkoutRouteBuilder(date)

    fun exercisesScreen(date: OffsetDateTime): ExercisesRouteBuilder = ExercisesRouteBuilder(date)
    fun exerciseScreen(): ExerciseRouteBuilder = ExerciseRouteBuilder()
}