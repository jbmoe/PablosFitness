package com.hyperborge.pablosfitness.data.local.converter

import androidx.room.TypeConverter
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import java.time.OffsetDateTime

class OffsetDateTimeConverter {
    @TypeConverter
    fun toOffsetDateTime(value: String): OffsetDateTime {
        return DateTimeHelper.getOffsetDateTimeFromString(value)
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime): String {
        return DateTimeHelper.getStringFromOffsetDateTime(date)
    }
}