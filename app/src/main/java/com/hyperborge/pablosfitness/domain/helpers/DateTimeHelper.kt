package com.hyperborge.pablosfitness.domain.helpers

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun getOffsetDateTimeFromString(value: String): OffsetDateTime {
        return OffsetDateTime.parse(value, formatter)
    }

    fun convertEpochToOffsetDateTime(epochValue: Long): OffsetDateTime {
        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(epochValue / 1000, 0, ZoneOffset.UTC), ZoneOffset.UTC)
    }

    fun getStringFromOffsetDateTime(value: OffsetDateTime): String {
        return formatter.format(value)
    }
}

