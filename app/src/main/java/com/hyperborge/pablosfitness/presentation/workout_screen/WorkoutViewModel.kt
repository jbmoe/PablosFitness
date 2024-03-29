package com.hyperborge.pablosfitness.presentation.workout_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyperborge.pablosfitness.data.local.model.*
import com.hyperborge.pablosfitness.data.repository.DbRepository
import com.hyperborge.pablosfitness.domain.extensions.WorkoutExtensions.mapToPresentationModel
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state = mutableStateOf(
        WorkoutState(
            exercise = Exercise(
                name = "",
                category = ExerciseCategory.Abs,
                type = ExerciseType.WeightAndReps
            )
        )
    )
    val state: State<WorkoutState> = _state

    private val _uiEventChannel = Channel<WorkoutUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    private var _weightUnit: WeightUnit = WeightUnit.KG
    private var _distanceUnit: DistanceUnit = DistanceUnit.M

    init {
        var workoutId = -1
        var exerciseId = -1

        savedStateHandle.get<Int>(NavConstants.PARAM_WORKOUT_ID)?.let { id ->
            workoutId = id
        }
        savedStateHandle.get<Int>(NavConstants.PARAM_EXERCISE_ID)?.let { id ->
            exerciseId = id
        }
        savedStateHandle.get<String>(NavConstants.PARAM_DATE)?.let { date ->
            _state.value = _state.value.copy(
                date = DateTimeHelper.getOffsetDateTimeFromString(date)
            )
        }

        if (workoutId != -1) {
            initWorkout(workoutId)
        } else if (exerciseId != -1) {
            initLatestWorkout(exerciseId)
        }
    }

    fun onEvent(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.ClearValues -> clearValues()
            is WorkoutEvent.DistanceChanged -> setDistance(event.distance)
            is WorkoutEvent.RepsChanged -> setReps(event.reps)
            is WorkoutEvent.SaveWorkout -> saveWorkout()
            is WorkoutEvent.DurationChanged -> setTime(event.duration)
            is WorkoutEvent.WeightChanged -> setWeight(event.weight)
            is WorkoutEvent.DistanceUnitChanged -> setUnit(event.item)
            is WorkoutEvent.WeighUnitChanged -> setUnit(event.item)
            is WorkoutEvent.CopyWorkout -> copyWorkout(event.workout)
        }
    }

    private fun copyWorkout(workout: WorkoutPresentationModel) {
        _state.value = _state.value.copy(
            weight = _state.value.weight.setValueAndClearError(workout.weight.toString()),
            weightUnit = _state.value.weightUnit.setValueAndClearError(workout.weightUnit.toString()),
            reps = _state.value.reps.setValueAndClearError(workout.reps.toString()),
            distance = _state.value.distance.setValueAndClearError(workout.distance.toString()),
            distanceUnit = _state.value.distance.setValueAndClearError(workout.distanceUnit.toString()),
            duration = _state.value.duration.setValueAndClearError(
                workout.duration ?: Duration.ZERO
            ),
        )
    }

    private fun setUnit(item: DistanceUnit) {
        _state.value = _state.value.copy(
            distanceUnit = _state.value.distanceUnit.setValueAndClearError(item.toString())
        )
        _distanceUnit = item
    }

    private fun setUnit(item: WeightUnit) {
        _state.value = _state.value.copy(
            weightUnit = _state.value.weightUnit.setValueAndClearError(item.toString())
        )
        _weightUnit = item
    }

    private fun clearValues() {
        _state.value = _state.value.copy(
            weight = _state.value.weight.setValueAndClearError("0.0"),
            reps = _state.value.reps.setValueAndClearError("0"),
            distance = _state.value.distance.setValueAndClearError("0.0"),
            duration = _state.value.duration.setValueAndClearError(Duration.ZERO),
        )
    }

    private fun setDistance(distance: String) {
        _state.value = _state.value.copy(
            distance = _state.value.distance.setValueAndClearError(distance)
        )
    }

    private fun setTime(duration: Duration) {
        if (duration < Duration.ZERO) return
        _state.value = _state.value.copy(
            duration = _state.value.duration.setValueAndClearError(duration),
        )
    }

    private fun setWeight(weight: String) {
        _state.value = _state.value.copy(
            weight = _state.value.weight.setValueAndClearError(weight)
        )
    }

    private fun setReps(reps: String) {
        _state.value = _state.value.copy(
            reps = _state.value.reps.setValueAndClearError(reps),
        )
    }

    private fun saveWorkout() {
        when (_state.value.exercise.type) {
            ExerciseType.WeightAndReps -> {
                val weight = _state.value.weight.value.toDoubleOrNull()
                val reps = _state.value.reps.value.toIntOrNull()

                if (weight == null || reps == null) {
                    if (weight == null)
                        _state.value = _state.value.copy(
                            weight = _state.value.weight.setError("Invalid weight")
                        )
                    if (reps == null)
                        _state.value = _state.value.copy(
                            reps = _state.value.reps.setError("Invalid reps")
                        )
                    return
                }

                if (weight > 0 && reps > 0) {
                    val workout = WorkoutWithExercise(
                        exercise = _state.value.exercise,
                        workout = Workout(
                            id = _state.value.workoutId,
                            exerciseId = _state.value.exercise.id!!,
                            weight = weight,
                            weightUnit = _weightUnit,
                            reps = reps,
                            createdAt = _state.value.date,
                            updatedAt = OffsetDateTime.now(),
                        )
                    )
                    viewModelScope.launch {
                        dbRepository.insertWorkout(workout)
                        launchUiEvent(WorkoutUiEvent.WorkoutSaved)
                    }
                    return
                }

                if (weight < 0) {
                    _state.value = _state.value.copy(
                        weight = _state.value.weight.setError("Invalid weight")
                    )
                }

                if (reps <= 0) {
                    _state.value = _state.value.copy(
                        reps = _state.value.reps.setError("Invalid reps")
                    )
                }
            }
            ExerciseType.DistanceAndTime -> {
                val distance = _state.value.distance.value.toDoubleOrNull()
                val duration = _state.value.duration.value

                if (distance == null) {
                    _state.value = _state.value.copy(
                        distance = _state.value.distance.setError("Invalid distance")
                    )
                    return
                }

                if (distance >= 0 && duration >= Duration.ZERO) {
                    val workout = WorkoutWithExercise(
                        exercise = _state.value.exercise,
                        workout = Workout(
                            id = _state.value.workoutId,
                            exerciseId = _state.value.exercise.id!!,
                            distance = distance,
                            distanceUnit = _distanceUnit,
                            timeInSeconds = duration.inWholeSeconds.toInt(),
                            createdAt = _state.value.date,
                            updatedAt = OffsetDateTime.now(),
                        )
                    )
                    viewModelScope.launch {
                        dbRepository.insertWorkout(workout)
                        launchUiEvent(WorkoutUiEvent.WorkoutSaved)
                    }
                    return
                }

                if (distance < 0) {
                    _state.value = _state.value.copy(
                        distance = _state.value.distance.setError("Invalid distance")
                    )
                }

                if (duration < Duration.ZERO) {
                    _state.value = _state.value.copy(
                        duration = _state.value.duration.setError("Invalid time")
                    )
                }
            }
        }
    }

    private fun initWorkout(id: Int) {
        viewModelScope.launch {
            dbRepository.getWorkout(id).onEach { workout ->
                _state.value = _state.value.copy(
                    exercise = workout.exercise,
                    weight = _state.value.weight.setValueAndClearError(
                        workout.workout.weight.toString()
                    ),
                    weightUnit = _state.value.weightUnit.setValueAndClearError(
                        workout.workout.weightUnit.toString()
                    ),
                    reps = _state.value.reps.setValueAndClearError(
                        workout.workout.reps.toString()
                    ),
                    distance = _state.value.distance.setValueAndClearError(
                        workout.workout.distance.toString()
                    ),
                    distanceUnit = _state.value.distanceUnit.setValueAndClearError(
                        workout.workout.distanceUnit.toString()
                    ),
                    duration = _state.value.duration.setValueAndClearError(
                        (workout.workout.timeInSeconds ?: 0).toDuration(DurationUnit.SECONDS)
                    ),
                    workoutId = workout.workout.id,
                    date = workout.workout.createdAt
                )
                _weightUnit = workout.workout.weightUnit
                _distanceUnit = workout.workout.distanceUnit
                initHistory(workout.exercise, workoutId = workout.workout.id)
            }.launchIn(viewModelScope)
        }
    }

    private fun initLatestWorkout(exerciseId: Int) {
        viewModelScope.launch {
            val workout = dbRepository.getNewestWorkoutWithExercise(exerciseId)
            if (workout != null) {
                _state.value = _state.value.copy(
                    exercise = workout.exercise,
                    weight = _state.value.weight.setValueAndClearError(
                        workout.workout.weight.toString()
                    ),
                    weightUnit = _state.value.weightUnit.setValueAndClearError(
                        workout.workout.weightUnit.toString()
                    ),
                    reps = _state.value.reps.setValueAndClearError(
                        workout.workout.reps.toString()
                    ),
                    distance = _state.value.distance.setValueAndClearError(
                        workout.workout.distance.toString()
                    ),
                    distanceUnit = _state.value.distanceUnit.setValueAndClearError(
                        workout.workout.distanceUnit.toString()
                    ),
                    duration = _state.value.duration.setValueAndClearError(
                        (workout.workout.timeInSeconds ?: 0).toDuration(DurationUnit.SECONDS)
                    )
                )
                _weightUnit = workout.workout.weightUnit
                _distanceUnit = workout.workout.distanceUnit
                initHistory(workout.exercise, workout.workout.id!!)
            } else {
                initExercise(exerciseId)
            }
        }
    }

    private fun initExercise(id: Int) {
        viewModelScope.launch {
            dbRepository.getExercise(id).onEach { exercise ->
                _state.value = _state.value.copy(exercise = exercise)
            }.launchIn(viewModelScope)
        }
    }

    private fun initHistory(exercise: Exercise, workoutId: Int? = null) {
        val exerciseId = exercise.id!!
        viewModelScope.launch {
            dbRepository.getWorkoutsWithExercise(exerciseId).onEach { workouts ->
                _state.value = _state.value.copy(history = workouts.mapToPresentationModel())
            }.launchIn(viewModelScope)

            dbRepository.getWorkoutStats(
                exerciseId = exerciseId,
                workoutId = workoutId,
                weightUnit = _weightUnit,
                distanceUnit = _distanceUnit,
                from = OffsetDateTime.MIN,
                to = OffsetDateTime.MAX
            ).onEach {
                _state.value = _state.value.copy(workoutStats = it)
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun launchUiEvent(event: WorkoutUiEvent) {
        _uiEventChannel.send(event)
    }
}