package com.hyperborge.pablosfitness.domain.extensions

import java.time.OffsetDateTime

object OffsetDateTimeExtensions {
    fun OffsetDateTime.getStartOfDay(): OffsetDateTime {
        return this
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
    }

    fun OffsetDateTime.atEndOfDate(): OffsetDateTime {
        return this
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
    }
}