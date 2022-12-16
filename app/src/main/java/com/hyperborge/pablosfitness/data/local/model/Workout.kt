package com.hyperborge.pablosfitness.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    @ColumnInfo(name = "reps") val reps: Int? = null,
    @ColumnInfo(name = "weight") val weight: Double? = null,
    @ColumnInfo(name = "weight_unit") val weightUnit: WeightUnit = WeightUnit.KG,
    @ColumnInfo(name = "distance") val distance: Double? = null,
    @ColumnInfo(name = "distance_unit") val distanceUnit: DistanceUnit = DistanceUnit.KM,
    @ColumnInfo(name = "time_in_seconds") val timeInSeconds: Int? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long
)

