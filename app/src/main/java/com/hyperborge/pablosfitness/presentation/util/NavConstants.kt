package com.hyperborge.pablosfitness.presentation.util

import com.hyperborge.pablosfitness.domain.extensions.ListExtensions.joinToStringOrNull

object NavConstants {
    const val PARAM_WORKOUT_ID = "workoutId"
    const val PARAM_EXERCISE_ID = "exerciseId"
    const val PARAM_DATE = "date"
}

object NavRouteBuilder {
    fun buildRouteDefinition(route: String, queryParams: List<String>? = null): String {
        val query = queryParams?.joinToStringOrNull(prefix = "?", separator = "&") { key ->
            "$key={$key}"
        } ?: ""

        return route + query
    }

    fun buildRoute(route: String, queryParams: List<Pair<String, Any>>? = null): String {
        val query = queryParams
            ?.joinToStringOrNull(prefix = "?", separator = "&") { (key, value) ->
                "$key=${value}"
            } ?: ""

        return route + query
    }
}