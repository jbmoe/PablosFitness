package com.hyperborge.pablosfitness.domain.helpers

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun getOffsetDateTimeFromString(value: String): OffsetDateTime {
        return OffsetDateTime.parse(value, formatter)
    }

    fun getStringFromOffsetDateTime(value: OffsetDateTime): String {
        return formatter.format(value)
    }
}

