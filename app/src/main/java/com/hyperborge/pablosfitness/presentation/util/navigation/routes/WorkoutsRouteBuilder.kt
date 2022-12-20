package com.hyperborge.pablosfitness.presentation.util.navigation.routes

import com.hyperborge.pablosfitness.domain.extensions.StringExtensions.utf8Encode
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants
import java.time.OffsetDateTime

class WorkoutsRouteBuilder(date: OffsetDateTime) : RouteBuilder {
    override val route: String = NavConstants.WORKOUTS_SCREEN
    override val queryParams: MutableList<Pair<String, Any>> = mutableListOf()

    init {
        queryParams.add(
            NavConstants.PARAM_DATE to DateTimeHelper.getStringFromOffsetDateTime(date).utf8Encode()
        )
    }
}