package com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants

class WorkoutRouteDefinitionBuilder : RouteDefinitionBuilder {
    override val route: String = NavConstants.WORKOUT_SCREEN
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(NavConstants.PARAM_EXERCISE_ID) {
            type = NavType.IntType
            defaultValue = -1
        },
        navArgument(NavConstants.PARAM_WORKOUT_ID) {
            type = NavType.IntType
            defaultValue = -1
        },
        navArgument(NavConstants.PARAM_DATE) {
            type = NavType.StringType
            nullable = true
        }
    )
}