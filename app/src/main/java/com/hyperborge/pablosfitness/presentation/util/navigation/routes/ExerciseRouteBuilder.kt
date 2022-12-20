package com.hyperborge.pablosfitness.presentation.util.navigation.routes

import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants

class ExerciseRouteBuilder : RouteBuilder {
    override val route: String = NavConstants.EXERCISE_SCREEN
    override val queryParams: MutableList<Pair<String, *>> = mutableListOf()

    fun withExerciseId(exerciseId: Int): ExerciseRouteBuilder {
        queryParams.add(NavConstants.PARAM_EXERCISE_ID to exerciseId)
        return this
    }
}