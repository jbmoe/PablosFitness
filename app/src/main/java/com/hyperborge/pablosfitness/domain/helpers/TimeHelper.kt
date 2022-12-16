package com.hyperborge.pablosfitness.domain.helpers

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

object DateHelper {

    fun getStartOfDateInEpochSeconds(date: LocalDateTime): Long {
        val newDate = date
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
        return getEpochSecondsFromLocalDateTime(newDate)
    }

    fun getEndOfDateInEpochSeconds(date: LocalDateTime): Long {
        val newDate = date
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
        return getEpochSecondsFromLocalDateTime(newDate)
    }

    fun getLocalDateFromEpochSeconds(epochSeconds: Long): LocalDate {
        return Instant.ofEpochSecond(epochSeconds).atOffset(ZoneOffset.UTC).toLocalDate()
    }

    fun getEpochSecondsFromLocalDateTime(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC)
    }
}