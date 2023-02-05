package com.hyperborge.pablosfitness.presentation.exercise_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.presentation.components.BetterTextField
import com.hyperborge.pablosfitness.presentation.components.ButtonsRow
import com.hyperborge.pablosfitness.presentation.components.PabloDropDown
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme

@Composable
fun ExerciseScreen(
    navController: NavController,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is ExerciseUiEvent.ExerciseSaved -> navController.popBackStack()
            }
        }
    }

    Content(
        state = state,
        onBack = {
            navController.popBackStack()
        }
    ) { event ->
        viewModel.onEvent(event)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Content(state: ExerciseState, onBack: () -> Unit, onEvent: (ExerciseEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val titleText = if (state.id != null) {
                        stringResource(R.string.update_exercise)
                    } else {
                        stringResource(R.string.new_exercise)
                    }
                    Text(text = titleText)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            BetterTextField(
                inputState = state.name,
                onValueChange = {
                    onEvent(ExerciseEvent.NameEntered(it))
                }
            )

            var categoriesExpanded by remember { mutableStateOf(false) }
            PabloDropDown(
                selectedItem = state.category,
                items = state.categories,
                expanded = categoriesExpanded,
                onExpandedChange = { categoriesExpanded = it }
            ) { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item.toString())
                    },
                    onClick = {
                        categoriesExpanded = false
                        onEvent(ExerciseEvent.CategoryPicked(item))
                    }
                )
            }

            var typesExpanded by remember { mutableStateOf(false) }
            PabloDropDown(
                selectedItem = state.type,
                items = state.types,
                expanded = typesExpanded,
                onExpandedChange = { typesExpanded = it }
            ) { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item.toString())
                    },
                    onClick = {
                        typesExpanded = false
                        onEvent(ExerciseEvent.TypePicked(item))
                    }
                )
            }

            ButtonsRow(
                positiveText = stringResource(id = R.string.save),
                onPositiveAction = { onEvent(ExerciseEvent.SaveExercise) },
                negativeText = stringResource(id = R.string.cancel),
                onNegativeAction = onBack
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    val state = ExerciseState(
        categories = ExerciseCategory.getAll(),
        types = ExerciseType.values().toList()
    )
    PablosFitnessTheme {
        Content(
            state = state,
            onBack = {}
        ) {}
    }
}
