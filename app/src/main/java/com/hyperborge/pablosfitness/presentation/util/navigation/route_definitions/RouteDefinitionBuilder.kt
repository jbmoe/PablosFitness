package com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions

import androidx.navigation.NamedNavArgument
import com.hyperborge.pablosfitness.domain.extensions.ListExtensions.joinToStringOrNull

sealed interface RouteDefinitionBuilder {
    val route: String
    val arguments: List<NamedNavArgument>

    fun build(): String {
        val query = arguments.joinToStringOrNull(prefix = "?", separator = "&") { arg ->
            "${arg.name}={${arg.name}}"
        } ?: ""

        return route + query
    }
}

