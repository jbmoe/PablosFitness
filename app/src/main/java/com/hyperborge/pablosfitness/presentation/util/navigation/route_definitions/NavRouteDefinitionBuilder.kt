package com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions

object NavRouteDefinitionBuilder {
    fun workoutsScreen(): WorkoutsRouteDefinitionBuilder = WorkoutsRouteDefinitionBuilder()
    fun workoutScreen(): WorkoutRouteDefinitionBuilder = WorkoutRouteDefinitionBuilder()
    fun exercisesScreen(): ExercisesRouteDefinitionBuilder = ExercisesRouteDefinitionBuilder()
    fun exerciseScreen(): ExerciseRouteDefinitionBuilder = ExerciseRouteDefinitionBuilder()
}