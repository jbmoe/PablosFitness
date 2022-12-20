package com.hyperborge.pablosfitness.presentation.util.navigation.routes

import com.hyperborge.pablosfitness.domain.extensions.ListExtensions.joinToStringOrNull

sealed interface RouteBuilder {
    val route: String
    val queryParams: List<Pair<String, *>>

    fun build(): String {
        val query = queryParams.joinToStringOrNull(prefix = "?", separator = "&") { (key, value) ->
            "$key=${value}"
        } ?: ""

        return route + query
    }
}