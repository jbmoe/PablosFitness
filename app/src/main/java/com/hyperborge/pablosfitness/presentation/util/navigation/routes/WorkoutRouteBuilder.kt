package com.hyperborge.pablosfitness.presentation.util.navigation.routes

import com.hyperborge.pablosfitness.domain.extensions.StringExtensions.utf8Encode
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants
import java.time.OffsetDateTime

class WorkoutRouteBuilder(date: OffsetDateTime) : RouteBuilder {
    override val route: String = NavConstants.WORKOUT_SCREEN
    override val queryParams: MutableList<Pair<String, Any>> = mutableListOf()

    init {
        queryParams.add(
            NavConstants.PARAM_DATE to DateTimeHelper.getStringFromOffsetDateTime(date).utf8Encode()
        )
    }

    fun withWorkoutId(id: Int): WorkoutRouteBuilder {
        queryParams.add(NavConstants.PARAM_WORKOUT_ID to id)
        return this
    }

    fun withExerciseId(id: Int): WorkoutRouteBuilder {
        queryParams.add(NavConstants.PARAM_EXERCISE_ID to id)
        return this
    }
}