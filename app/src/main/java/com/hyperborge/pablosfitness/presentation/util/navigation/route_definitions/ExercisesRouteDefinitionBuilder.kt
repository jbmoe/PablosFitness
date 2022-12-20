package com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants

class ExercisesRouteDefinitionBuilder : RouteDefinitionBuilder {
    override val route: String = NavConstants.EXERCISES_SCREEN
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(NavConstants.PARAM_DATE) {
            type = NavType.StringType
            nullable = true
        }
    )
}