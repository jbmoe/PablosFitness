package com.hyperborge.pablosfitness.presentation.workouts_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.common.TestData
import com.hyperborge.pablosfitness.domain.extensions.mapToPresentationModel
import com.hyperborge.pablosfitness.domain.helpers.DateHelper
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.NavConstants
import com.hyperborge.pablosfitness.presentation.util.NavRouteBuilder
import com.hyperborge.pablosfitness.presentation.util.Screen
import com.hyperborge.pablosfitness.presentation.workouts_screen.components.DateCircusComponent
import com.hyperborge.pablosfitness.presentation.workouts_screen.components.WorkoutComponent
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.random.Random

@Composable
fun WorkoutsScreen(
    navController: NavController,
    viewModel: WorkoutsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is WorkoutsUiEvent.WorkoutsDeleted -> {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = context.getString(
                                R.string.n_deleted,
                                event.workouts.count()
                            ),
                            actionLabel = context.getString(R.string.restore),
                            duration = SnackbarDuration.Long
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(WorkoutsEvent.RestoreDeletion)
                        }
                    }
                }
            }
        }
    }

    Content(
        state = state,
        snackbarHostState = snackbarHostState,
        onAddWorkout = {
            navController.navigate(
                route = NavRouteBuilder.buildRoute(
                    Screen.ExercisesScreen.route,
                    listOf(
                        NavConstants.PARAM_DATE to "${
                            DateHelper.getEpochSecondsFromLocalDateTime(state.date)
                        }"
                    )
                )
            )
        },
        onWorkoutClicked = { workoutPresentationModel ->
            navController.navigate(
                route = NavRouteBuilder.buildRoute(
                    route = Screen.WorkoutScreen.route,
                    queryParams = listOf(
                        NavConstants.PARAM_WORKOUT_ID to workoutPresentationModel.id
                    )
                )
            )
        }
    ) { event ->
        viewModel.onEvent(event)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: WorkoutsState,
    snackbarHostState: SnackbarHostState,
    onAddWorkout: () -> Unit,
    onWorkoutClicked: (WorkoutPresentationModel) -> Unit,
    onEvent: (WorkoutsEvent) -> Unit
) {
    val workouts = state.workouts
    Scaffold(
        topBar = {
            TopBarComponent(
                date = state.date,
                onAddWorkout = onAddWorkout,
                onEvent = onEvent
            )
        },
        bottomBar = {
            BottomBarComponent(state, onEvent)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        val hapticFeedback = LocalHapticFeedback.current
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            if (workouts.isNotEmpty()) {
                items(workouts, key = { it.id }) { workout ->
                    WorkoutComponent(
                        modifier = Modifier.fillMaxWidth(),
                        exercise = workout,
                        onLongClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onEvent(WorkoutsEvent.ToggleWorkoutMarked(workout))
                        }
                    ) {
                        if (state.isMarking) {
                            onEvent(WorkoutsEvent.ToggleWorkoutMarked(workout))
                        } else {
                            onWorkoutClicked(workout)
                        }
                    }
                }
            } else {
                item {
                    EmptyLogContent(onAddWorkout = onAddWorkout)
                }
            }
        }
    }
}

@Composable
private fun BottomBarComponent(
    state: WorkoutsState,
    onEvent: (WorkoutsEvent) -> Unit
) {
    if (state.isMarking) {
        val context = LocalContext.current
        val markedCount = state.workouts.count { it.isMarked }
        val checkboxState = if (state.workouts.size == markedCount) {
            ToggleableState.On
        } else if (markedCount == 0) {
            ToggleableState.Off
        } else {
            ToggleableState.Indeterminate
        }

        BottomAppBar(
            actions = {
                TriStateCheckbox(
                    state = checkboxState,
                    onClick = {
                        when (checkboxState) {
                            ToggleableState.On -> onEvent(WorkoutsEvent.ClearMarkedWorkouts)
                            ToggleableState.Off,
                            ToggleableState.Indeterminate -> onEvent(WorkoutsEvent.MarkAllWorkouts)
                        }
                    }
                )
                Text(text = context.getString(R.string.n_marked, markedCount))
            },
            floatingActionButton = {
                SmallFloatingActionButton(onClick = { onEvent(WorkoutsEvent.DeleteMarkedWorkouts) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    date: LocalDateTime,
    onAddWorkout: () -> Unit,
    onEvent: (WorkoutsEvent) -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.workouts))
            },
            actions = {
                IconButton(onClick = {
                    onAddWorkout()
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        )
        DateCircusComponent(
            modifier = Modifier.fillMaxWidth(),
            date = date,
            onResetToToday = { onEvent(WorkoutsEvent.ResetDate) },
            onPrevDateClicked = { onEvent(WorkoutsEvent.PreviousDate) },
            onNextDayClicked = { onEvent(WorkoutsEvent.NextDate) }
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun EmptyLogContent(onAddWorkout: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(256.dp))
            Text(
                text = stringResource(R.string.empty_workout_log),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(64.dp))
            IconButton(onClick = onAddWorkout) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
            }
            Text(text = stringResource(R.string.start_new_workout))
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    val data = TestData.workoutsWithExercises().mapToPresentationModel()
        .map { it.copy(isMarked = Random.nextBoolean()) }
    val state = WorkoutsState(
        date = LocalDateTime.now(),
        workouts = data,
    )
    PablosFitnessTheme {
        Content(
            state = state,
            snackbarHostState = SnackbarHostState(),
            onAddWorkout = {},
            onWorkoutClicked = {},
            onEvent = {}
        )
    }
}