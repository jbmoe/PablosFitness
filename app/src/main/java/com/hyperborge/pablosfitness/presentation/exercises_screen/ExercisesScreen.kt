package com.hyperborge.pablosfitness.presentation.exercises_screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.common.TestData
import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.domain.extensions.ExerciseExtensions.mapToPresentationModel
import com.hyperborge.pablosfitness.domain.extensions.StringExtensions.utf8Encode
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import com.hyperborge.pablosfitness.presentation.exercises_screen.components.ExerciseCategoryItem
import com.hyperborge.pablosfitness.presentation.exercises_screen.components.ExerciseItem
import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.NavConstants
import com.hyperborge.pablosfitness.presentation.util.NavRouteBuilder
import com.hyperborge.pablosfitness.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun ExercisesScreen(
    navController: NavController,
    viewModel: ExercisesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is ExercisesUiEvent.ExerciseDeleted -> {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = context.getString(
                                R.string.x_deleted,
                                event.exercise.name
                            ),
                            actionLabel = context.getString(R.string.restore),
                            duration = SnackbarDuration.Long
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(ExercisesEvent.RestoreDeletion)
                        }
                    }
                }
            }
        }
    }

    Content(
        exercises = state.exercises,
        snackbarHostState = snackbarHostState,
        onExerciseClicked = { exercise ->
            val dateAsString = DateTimeHelper
                .getStringFromOffsetDateTime(value = state.workoutDate)
                .utf8Encode()
            navController.navigate(
                route = NavRouteBuilder.buildRoute(
                    Screen.WorkoutScreen.route,
                    listOf(
                        NavConstants.PARAM_EXERCISE_ID to "${exercise.id}",
                        NavConstants.PARAM_DATE to dateAsString
                    )
                )
            )
        },
        onAddEditExercise = { exercise ->
            navController.navigate(
                route = NavRouteBuilder.buildRoute(
                    Screen.ExerciseScreen.route,
                    listOf(NavConstants.PARAM_EXERCISE_ID to (exercise?.id ?: -1))
                )
            )
        },
        onBack = { navController.popBackStack() }
    ) { event ->
        viewModel.onEvent(event)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Content(
    exercises: Map<ExerciseCategory, List<ExercisePresentationModel>>,
    snackbarHostState: SnackbarHostState,
    onExerciseClicked: (ExercisePresentationModel) -> Unit,
    onAddEditExercise: (ExercisePresentationModel?) -> Unit,
    onBack: () -> Unit,
    onEvent: (ExercisesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.exercises))
                },
                actions = {
                    IconButton(onClick = {
                        onAddEditExercise(null)
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            exercises.forEach { (category, exercises) ->
                if (exercises.isNotEmpty()) {
                    stickyHeader {
                        ExerciseCategoryItem(exerciseCategory = category)
                    }
                    itemsIndexed(items = exercises) { index, exercise ->
                        ExerciseItem(
                            modifier = Modifier.padding(start = 16.dp),
                            exercise = exercise,
                            onEdit = { onAddEditExercise(exercise) },
                            onDelete = { onEvent(ExercisesEvent.DeleteExercise(exercise)) }
                        ) {
                            onExerciseClicked(exercise)
                        }
                        if (index != exercises.lastIndex) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                thickness = Dp.Hairline
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    val data = TestData
        .weightExercises()
        .union(TestData.distanceExercises())
        .toList()
    val state = ExercisesState(
        exercises = ExerciseCategory.getAll().associateWith { exerciseCategory ->
            data.filter { exercise ->
                exercise.category == exerciseCategory
            }.mapToPresentationModel()
        },
        expandedExerciseCategoriesMap = ExerciseCategory.getAll().associateWith { false }
            .toMutableMap()
    )
    PablosFitnessTheme {
        Content(
            exercises = state.exercises,
            snackbarHostState = SnackbarHostState(),
            onExerciseClicked = {},
            onAddEditExercise = {},
            onBack = {},
            {}
        )
    }
}