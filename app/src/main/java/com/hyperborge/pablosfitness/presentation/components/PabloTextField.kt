package com.hyperborge.pablosfitness.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import com.hyperborge.pablosfitness.presentation.util.InputFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    errorText: String? = null,
    labelText: String? = null,
    placeholderText: String? = null,
    supportingText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        val supporting = if (isError) {
            textOrNull(errorText)
        } else {
            textOrNull(supportingText)
        }

        OutlinedTextField(
            modifier = modifier,
            value = value,
            enabled = enabled,
            onValueChange = onValueChange,
            isError = isError,
            visualTransformation = visualTransformation,
            textStyle = textStyle,
            label = textOrNull(labelText),
            singleLine = singleLine,
            maxLines = maxLines,
            readOnly = readOnly,
            placeholder = textOrNull(placeholderText),
            supportingText = supporting,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            colors = colors,
            interactionSource = interactionSource,
            shape = shape
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> BetterTextField(
    modifier: Modifier = Modifier,
    inputState: InputFieldState<T>,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    onValueChange: (String) -> Unit
) {
    BetterTextField(
        modifier = modifier,
        value = inputState.value?.toString() ?: "",
        enabled = inputState.isEnabled,
        isError = inputState.isError,
        errorText = inputState.error,
        labelText = inputState.label,
        placeholderText = inputState.placeholder.toString(),
        visualTransformation = visualTransformation,
        textStyle = textStyle,
        singleLine = singleLine,
        maxLines = maxLines,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        colors = colors,
        onValueChange = onValueChange,
        shape = shape,
        interactionSource = interactionSource
    )
}

private fun textOrNull(text: String?): @Composable (() -> Unit)? {
    return if (text.isNullOrBlank()) {
        null
    } else {
        { Text(text = text) }
    }
}