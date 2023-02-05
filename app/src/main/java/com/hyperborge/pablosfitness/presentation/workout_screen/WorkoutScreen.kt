package com.hyperborge.pablosfitness.presentation.workout_screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.common.TestData
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.domain.extensions.WorkoutExtensions.mapToPresentationModel
import com.hyperborge.pablosfitness.presentation.components.*
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.navigation.route_definitions.NavRouteDefinitionBuilder
import com.hyperborge.pablosfitness.presentation.util.navigation.routes.NavRouteBuilder
import com.hyperborge.pablosfitness.presentation.workout_screen.components.WorkoutHistoryDate
import com.hyperborge.pablosfitness.presentation.workout_screen.components.WorkoutHistoryItem

@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val localFocusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                WorkoutUiEvent.WorkoutSaved -> {
                    localFocusManager.clearFocus()
                    navController.navigate(
                        route = NavRouteBuilder
                            .workoutsScreen(viewModel.state.value.date)
                            .build()
                    ) {
                        popUpTo(
                            route = NavRouteDefinitionBuilder
                                .workoutsScreen()
                                .build()
                        ) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Content(
        state = viewModel.state.value,
        onBack = {
            navController.popBackStack()
        },
        onEditWorkout = { workoutPresentationModel ->
            localFocusManager.clearFocus()
            navController.navigate(
                route = NavRouteBuilder
                    .workoutScreen(viewModel.state.value.date)
                    .withWorkoutId(workoutPresentationModel.id)
                    .build()
            )
        }
    ) { event ->
        viewModel.onEvent(event)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: WorkoutState,
    onBack: () -> Unit,
    onEditWorkout: (WorkoutPresentationModel) -> Unit,
    onEvent: (WorkoutEvent) -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabRowTitles = listOf("Track", "History")
    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { localFocusManager.clearFocus() })
        },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = state.exercise.name) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        var visible by remember {
                            mutableStateOf(false)
                        }
                        IconButton(onClick = { visible = visible.not() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_question_mark_24),
                                contentDescription = null
                            )
                        }
                        if (visible) {
                            AlertDialog(
                                onDismissRequest = { visible = false },
                                title = { Text(text = "Records") },
                                text = {
                                    Column {
                                        if (state.exercise.type == ExerciseType.WeightAndReps) {
                                            Text(text = "Max weight: ${state.maxWeightWorkout?.weight ?: 0} ${state.maxWeightWorkout?.weightUnit}")
                                            Text(text = "Max reps: ${state.maxRepsWorkout?.reps ?: 0} reps")
                                        } else {
                                            Text(text = "Max distance: ${state.maxDistanceWorkout?.distance ?: 0} ${state.maxDistanceWorkout?.distanceUnit.toString()}")
                                            Text(text = "Max duration: ${state.maxDurationWorkout?.duration ?: 0}")
                                        }
                                    }
                                },
                                confirmButton = {}
                            )
                        }
                    }
                )
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabRowTitles.forEachIndexed { i, title ->
                        Tab(
                            selected = selectedTabIndex == i,
                            onClick = { selectedTabIndex = i },
                            text = { Text(text = title) })
                    }
                }
            }
        }
    ) { padding ->
        when (selectedTabIndex) {
            0 -> TrackWorkoutContent(padding, state, onEvent)
            1 -> ExerciseHistoryContent(padding, state, onEditWorkout) { event ->
                if (event is WorkoutEvent.CopyWorkout) {
                    selectedTabIndex = 0
                }
                onEvent(event)
            }
            else -> throw NotImplementedError()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseHistoryContent(
    padding: PaddingValues,
    state: WorkoutState,
    onEdit: (WorkoutPresentationModel) -> Unit,
    onEvent: (WorkoutEvent) -> Unit
) {
    val historyByDate = state.history.groupBy {
        it.createdAt
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        historyByDate.forEach { (date, workouts) ->
            if (workouts.isNotEmpty()) {
                stickyHeader {
                    WorkoutHistoryDate(date = date)
                }
                itemsIndexed(items = workouts) { index, workout ->
                    WorkoutHistoryItem(
                        modifier = Modifier.padding(start = 16.dp),
                        workout = workout,
                        onEdit = { onEdit(workout) },
                        onCopy = {
                            onEvent(WorkoutEvent.CopyWorkout(workout))
                        }
                    )
                    if (index != workouts.lastIndex) {
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TrackWorkoutContent(
    padding: PaddingValues,
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding() + 16.dp,
                start = padding.calculateStartPadding(LocalLayoutDirection.current),
                end = padding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = padding.calculateBottomPadding()
            )
            .consumeWindowInsets(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        when (state.exercise.type) {
            ExerciseType.WeightAndReps -> WeightAndRepsContent(state, onEvent)
            ExerciseType.DistanceAndTime -> DistanceAndTimeContent(state, onEvent)
        }

        Divider(
            modifier = Modifier.fillMaxWidth(.95f),
            color = MaterialTheme.colorScheme.tertiary,
            thickness = Dp.Hairline
        )

        ButtonsRow(
            positiveText = stringResource(id = R.string.save),
            onPositiveAction = {
                onEvent(WorkoutEvent.SaveWorkout)
            },
            negativeText = stringResource(id = R.string.clear),
            onNegativeAction = {
                onEvent(WorkoutEvent.ClearValues)
            }
        )
    }
}

@Composable
private fun WeightAndRepsContent(state: WorkoutState, onEvent: (WorkoutEvent) -> Unit) {
    PabloDoublePicker(
        modifier = Modifier.width(196.dp),
        inputState = state.weight,
        stepSize = 5.0,
        onValueChanged = { weight ->
            onEvent(WorkoutEvent.WeightChanged(weight))
        }
    )

    var expanded by remember { mutableStateOf(false) }
    PabloDropDown(
        modifier = Modifier.width(196.dp),
        selectedItem = state.weightUnit,
        items = state.weightUnits,
        expanded = expanded,
        onExpandedChange = { expanded = it },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    ) { item ->
        DropdownMenuItem(
            text = { Text(text = item.toString()) },
            onClick = {
                expanded = false
                onEvent(WorkoutEvent.WeighUnitChanged(item))
            }
        )
    }

    PabloIntPicker(
        modifier = Modifier.width(196.dp),
        inputState = state.reps,
        onValueChanged = { reps ->
            onEvent(WorkoutEvent.RepsChanged(reps))
        }
    )
}


@Composable
private fun DistanceAndTimeContent(state: WorkoutState, onEvent: (WorkoutEvent) -> Unit) {
    PabloDoublePicker(
        modifier = Modifier.width(196.dp),
        inputState = state.distance
    ) { distance ->
        onEvent(WorkoutEvent.DistanceChanged(distance))
    }

    var expanded by remember { mutableStateOf(false) }
    PabloDropDown(
        modifier = Modifier.width(196.dp),
        selectedItem = state.distanceUnit,
        items = state.distanceUnits,
        expanded = expanded,
        textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
        onExpandedChange = { expanded = it }
    ) { item ->
        DropdownMenuItem(
            text = {
                Text(
                    text = item.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                )
            },
            onClick = {
                expanded = false
                onEvent(WorkoutEvent.DistanceUnitChanged(item))
            }
        )
    }

    PabloDurationPicker(
        modifier = Modifier.width(211.dp),
        state = state.duration,
        onValueChange = { onEvent(WorkoutEvent.DurationChanged(it)) }
    )
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    val state = WorkoutState(
        exercise = TestData.distanceExercises().random(),
        history = TestData.workoutsWithExercises(ExerciseType.DistanceAndTime)
            .plus(TestData.workoutsWithExercises(ExerciseType.WeightAndReps))
            .mapToPresentationModel()
    )
    PablosFitnessTheme {
        Content(
            state = state,
            onBack = {},
            onEditWorkout = {}
        ) {}
    }
}
