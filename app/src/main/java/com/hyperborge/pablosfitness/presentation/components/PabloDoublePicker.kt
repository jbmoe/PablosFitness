package com.hyperborge.pablosfitness.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.InputFieldState

@Composable
fun PabloDoublePicker(
    modifier: Modifier = Modifier,
    inputState: InputFieldState<Double> = InputFieldState(value = 0.0),
    stepSize: Double = 1.0,
    onValueChanged: (Double) -> Unit
) {
    PabloBaseNumberPicker(
        modifier = modifier,
        inputState = inputState,
        onSubtract = { onValueChanged(inputState.value - stepSize) },
        onAdd = { onValueChanged(inputState.value + stepSize) },
        onValueChange = { onValueChanged(it.toDoubleOrNull() ?: 0.0) }
    )
}

@Composable
fun PabloIntPicker(
    modifier: Modifier = Modifier,
    inputState: InputFieldState<Int> = InputFieldState(value = 0),
    stepSize: Int = 1,
    onValueChanged: (Int) -> Unit
) {
    PabloBaseNumberPicker(
        modifier = modifier,
        inputState = inputState,
        onSubtract = { onValueChanged(inputState.value - stepSize) },
        onAdd = { onValueChanged(inputState.value + stepSize) },
        onValueChange = { onValueChanged(it.toIntOrNull() ?: 0) }
    )
}

@Composable
private fun <T : Number> PabloBaseNumberPicker(
    modifier: Modifier = Modifier,
    inputState: InputFieldState<T>,
    onSubtract: () -> Unit,
    onAdd: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BetterTextField(
            modifier = modifier,
            inputState = inputState,
            leadingIcon = {
                IconButton(onClick = onSubtract) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = onAdd) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            onValueChange = onValueChange
        )
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            PabloDoublePicker(onValueChanged = {})
            PabloIntPicker(onValueChanged = {})
        }
    }
}