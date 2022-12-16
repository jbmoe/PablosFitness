package com.hyperborge.pablosfitness.presentation.exercise_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.data.repository.DbRepository
import com.hyperborge.pablosfitness.presentation.util.NavConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<ExerciseState> =
        mutableStateOf(
            ExerciseState(
                categories = ExerciseCategory.getAll(),
                types = ExerciseType.values().toList()
            )
        )
    val state: State<ExerciseState> = _state

    private val _uiEventChannel = Channel<ExerciseUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    private var _categoryInput: ExerciseCategory? = null
    private var _typeInput: ExerciseType? = null

    init {
        savedStateHandle.get<Int>(NavConstants.PARAM_EXERCISE_ID)?.let { id ->
            if (id != -1) {
                initState(id)
            }
        }
    }

    fun onEvent(event: ExerciseEvent) {
        when (event) {
            is ExerciseEvent.SaveExercise -> saveExercise()
            is ExerciseEvent.NameEntered -> updateName(event.value)
            is ExerciseEvent.CategoryPicked -> updateCategory(event.value)
            is ExerciseEvent.TypePicked -> updateType(event.value)
        }
    }

    private fun updateName(name: String) {
        _state.value = _state.value.copy(
            name = _state.value.name.setValueAndClearError(
                value = name
            )
        )
    }

    private fun updateCategory(category: ExerciseCategory) {
        _state.value = _state.value.copy(
            category = _state.value.category.setValueAndClearError(
                value = category.toString()
            )
        )
        _categoryInput = category
    }

    private fun updateType(type: ExerciseType) {
        _state.value = _state.value.copy(
            type = _state.value.type.setValueAndClearError(
                value = type.toString()
            )
        )
        _typeInput = type
    }

    private fun saveExercise() {
        val name = _state.value.name.value
        val category = _categoryInput
        val type = _typeInput

        if (name.isNotEmpty() && category != null && type != null) {
            val exercise = Exercise(
                id = _state.value.id,
                name = name,
                category = category,
                type = type
            )
            viewModelScope.launch {
                dbRepository.insertExercise(exercise = exercise)
                launchEvent(ExerciseUiEvent.ExerciseSaved)
            }
            return
        }

        if (name.isEmpty()) {
            _state.value = _state.value.copy(
                name = _state.value.name.setError(error = "Invalid name")
            )
        }

        if (category == null) {
            _state.value = _state.value.copy(
                category = _state.value.category.setError(error = "Invalid category")
            )
        }

        if (type == null) {
            _state.value = _state.value.copy(
                type = _state.value.type.setError(error = "Invalid type")
            )
        }
    }

    private fun initState(exerciseId: Int) {
        viewModelScope.launch {
            dbRepository.getExercise(exerciseId).onEach { exercise ->
                _state.value = _state.value.copy(
                    id = exercise.id,
                    name = _state.value.name.copy(value = exercise.name),
                    category = _state.value.category.copy(value = exercise.category.toString()),
                    type = _state.value.type.copy(value = exercise.type.toString())
                )
                _categoryInput = exercise.category
                _typeInput = exercise.type
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun launchEvent(event: ExerciseUiEvent) {
        _uiEventChannel.send(event)
    }
}