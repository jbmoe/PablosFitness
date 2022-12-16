package com.hyperborge.pablosfitness.presentation.workouts_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyperborge.pablosfitness.data.local.model.WorkoutWithExercise
import com.hyperborge.pablosfitness.data.repository.DbRepository
import com.hyperborge.pablosfitness.domain.extensions.mapToPresentationModel
import com.hyperborge.pablosfitness.domain.helpers.DateHelper
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import com.hyperborge.pablosfitness.presentation.util.NavConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableState<WorkoutsState> =
        mutableStateOf(WorkoutsState(date = LocalDateTime.now()))
    val state: State<WorkoutsState> = _state

    private var _recentlyDeletedWorkouts: List<WorkoutWithExercise> = emptyList()

    private val _uiEventChannel = Channel<WorkoutsUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    init {
        savedStateHandle.get<Long>(NavConstants.PARAM_DATE)?.let { date ->
            if (date > 0) {
                _state.value = _state.value.copy(
                    date = DateHelper.getLocalDateFromEpochSeconds(date).atTime(12, 0)
                )
            }
        }
        getWorkoutsForDate(_state.value.date)
    }

    fun onEvent(event: WorkoutsEvent) {
        when (event) {
            is WorkoutsEvent.NextDate -> getWorkoutsForNextDate()
            is WorkoutsEvent.PreviousDate -> getWorkoutsForPrevDate()
            is WorkoutsEvent.ResetDate -> getWorkoutsForToday()
            is WorkoutsEvent.RestoreDeletion -> restoreDeleted()
            is WorkoutsEvent.ClearMarkedWorkouts -> clearMarkedWorkouts()
            is WorkoutsEvent.DeleteMarkedWorkouts -> deleteMarkedWorkouts()
            is WorkoutsEvent.MarkAllWorkouts -> markAllWorkouts()
            is WorkoutsEvent.ToggleWorkoutMarked -> toggleWorkoutMarked(event.workout)
        }
    }

    private fun toggleWorkoutMarked(workout: WorkoutPresentationModel) {
        val workouts = _state.value.workouts.map {
            if (it == workout) {
                it.copy(isMarked = it.isMarked.not())
            } else {
                it
            }
        }
        _state.value = _state.value.copy(workouts = workouts)
    }

    private fun markAllWorkouts() {
        val workouts = _state.value.workouts.map { it.copy(isMarked = true) }
        _state.value = _state.value.copy(workouts = workouts)
    }

    private fun deleteMarkedWorkouts() {
        val markedWorkoutsIds = _state.value.workouts
            .filter { it.isMarked }
            .map { it.id }
        viewModelScope.launch {
            _recentlyDeletedWorkouts = dbRepository.deleteWorkoutsByIds(markedWorkoutsIds)
            launchEvent(WorkoutsUiEvent.WorkoutsDeleted(_recentlyDeletedWorkouts.mapToPresentationModel()))
        }
    }

    private fun clearMarkedWorkouts() {
        val unMarkedWorkouts = _state.value.workouts.map { it.copy(isMarked = false) }
        _state.value = _state.value.copy(workouts = unMarkedWorkouts)
    }

    private fun restoreDeleted() {
        viewModelScope.launch {
            if (_recentlyDeletedWorkouts.isNotEmpty()) {
                dbRepository.insertWorkouts(_recentlyDeletedWorkouts)
            }
        }
    }

    private fun getWorkoutsForToday() {
        getWorkoutsForDate(resetDate())
    }

    private fun getWorkoutsForNextDate() {
        getWorkoutsForDate(incrementDate())
    }

    private fun getWorkoutsForPrevDate() {
        getWorkoutsForDate(decrementDate())
    }

    private fun getWorkoutsForDate(date: LocalDateTime) {
        viewModelScope.launch {
            dbRepository.getWorkouts(
                from = DateHelper.getStartOfDateInEpochSeconds(date),
                to = DateHelper.getEndOfDateInEpochSeconds(date)
            ).onEach { workouts ->
                _state.value = _state.value.copy(
                    workouts = workouts.mapToPresentationModel()
                )
            }.launchIn(viewModelScope)
        }
    }

    private fun incrementDate(): LocalDateTime {
        _state.value = _state.value.copy(
            date = _state.value.date.plusDays(1)
        )

        return _state.value.date
    }

    private fun decrementDate(): LocalDateTime {
        _state.value = _state.value.copy(
            date = _state.value.date.minusDays(1)
        )

        return _state.value.date
    }

    private fun resetDate(): LocalDateTime {
        _state.value = _state.value.copy(date = LocalDateTime.now())
        return _state.value.date
    }

    private suspend fun launchEvent(event: WorkoutsUiEvent) {
        _uiEventChannel.send(event)
    }
}