package com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants

class ExerciseRouteDefinitionBuilder : RouteDefinitionBuilder {
    override val route: String = NavConstants.EXERCISE_SCREEN
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(NavConstants.PARAM_EXERCISE_ID) {
            type = NavType.IntType
            defaultValue = -1
        }
    )
}