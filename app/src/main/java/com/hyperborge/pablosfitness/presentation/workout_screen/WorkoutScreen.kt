package com.hyperborge.pablosfitness.presentation.workout_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.hyperborge.pablosfitness.presentation.components.*
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.NavConstants
import com.hyperborge.pablosfitness.presentation.util.NavRouteBuilder
import com.hyperborge.pablosfitness.presentation.util.Screen

@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                WorkoutUiEvent.WorkoutSaved -> {
                    navController.navigate(
                        route = NavRouteBuilder.buildRoute(
                            route = Screen.WorkoutsScreen.route,
                            queryParams = listOf(NavConstants.PARAM_DATE to viewModel.state.value.date)
                        )
                    ) {
                        popUpTo(
                            route = NavRouteBuilder.buildRoute(
                                route = Screen.WorkoutsScreen.route,
                                queryParams = listOf(NavConstants.PARAM_DATE to viewModel.state.value.date)
                            )
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
        }
    ) { event ->
        viewModel.onEvent(event)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Content(state: WorkoutState, onBack: () -> Unit, onEvent: (WorkoutEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.exercise.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumedWindowInsets(padding),
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
}

@Composable
fun WeightAndRepsContent(state: WorkoutState, onEvent: (WorkoutEvent) -> Unit) {
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
fun DistanceAndTimeContent(state: WorkoutState, onEvent: (WorkoutEvent) -> Unit) {
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
        exercise = TestData.distanceExercises().random()
    )
    PablosFitnessTheme {
        Content(
            state = state,
            onBack = {}
        ) {}
    }
}
