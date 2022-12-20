package com.hyperborge.pablosfitness.presentation.exercises_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.data.repository.DbRepository
import com.hyperborge.pablosfitness.domain.extensions.ExerciseExtensions.mapToPresentationModel
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel
import com.hyperborge.pablosfitness.presentation.util.navigation.NavConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableState<ExercisesState> = mutableStateOf(
        ExercisesState(
            exercises = ExerciseCategory
                .getAll()
                .associateWith {
                    emptyList()
                },
            expandedExerciseCategoriesMap = ExerciseCategory
                .getAll()
                .associateWith { false }
                .toMutableMap()
        )
    )
    val state: State<ExercisesState> = _state

    private var _recentlyDeletedExercise: Exercise? = null

    private val _uiEventChannel = Channel<ExercisesUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    init {
        savedStateHandle.get<String>(NavConstants.PARAM_DATE)?.let { date ->
            _state.value = _state.value.copy(
                workoutDate = DateTimeHelper.getOffsetDateTimeFromString(date)
            )
        }
        getAllExercises()
    }

    fun onEvent(event: ExercisesEvent) {
        when (event) {
            is ExercisesEvent.DeleteExercise -> deleteExercise(event.exercise)
            is ExercisesEvent.RestoreDeletion -> restoreDeletedExercise()
        }
    }

    private fun restoreDeletedExercise() {
        val deletedExercise = _recentlyDeletedExercise ?: return
        viewModelScope.launch {
            dbRepository.insertExercise(deletedExercise)
        }
    }

    private fun deleteExercise(exercise: ExercisePresentationModel) {
        viewModelScope.launch {
            val deleted = dbRepository.deleteExerciseById(exercise.id)
            _recentlyDeletedExercise = deleted
            launchUiEvent(ExercisesUiEvent.ExerciseDeleted(deleted.mapToPresentationModel()))
        }
    }

    private fun getAllExercises() {
        viewModelScope.launch {
            dbRepository.getExercises().onEach { exercises ->
                _state.value = _state.value.copy(
                    exercises = ExerciseCategory.getAll().associateWith { exerciseCategory ->
                        exercises.filter { exercise ->
                            exercise.category == exerciseCategory
                        }.mapToPresentationModel()
                    }
                )
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun launchUiEvent(event: ExercisesUiEvent) {
        _uiEventChannel.send(event)
    }
}