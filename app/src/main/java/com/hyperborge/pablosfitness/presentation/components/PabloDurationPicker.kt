package com.hyperborge.pablosfitness.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import com.hyperborge.pablosfitness.presentation.util.InputFieldState
import java.text.DecimalFormat
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PabloDurationPicker(
    modifier: Modifier = Modifier,
    state: InputFieldState<Duration>,
    onValueChange: (Duration) -> Unit
) {
    val value = state.value


    /** Converts CharSequence in format "hhmmss" to Duration */
    fun CharSequence.toDuration(): Duration {
        val hoursSequence = subSequence(0, 2)
        val minutesSequence = subSequence(2, 4)
        val secondsSequence = subSequence(4, 6)
        val sequenceToTimeMultiplier = listOf(
            hoursSequence to SECONDS_PER_MINUTE * MINUTES_PER_HOUR,
            minutesSequence to SECONDS_PER_MINUTE,
            secondsSequence to 1L,
        )

        val radix = 10
        var seconds = 0L
        for ((sequence, timeMultiplier) in sequenceToTimeMultiplier) {
            var baseMultiplier = 1
            for (char in sequence.reversed()) {
                val int = char.code - '0'.code
                seconds += int * baseMultiplier * timeMultiplier
                baseMultiplier *= radix
            }
        }

        return seconds.seconds
    }

    /** Converts Duration to String in format "hhmmss" */
    fun Duration.asString(): String {
        return toComponents { hours, minutes, seconds, _ ->
            "${
                hours.coerceAtMost(99).toInt().twoDigits()
            }${minutes.twoDigits()}${seconds.twoDigits()}"
        }
    }

    val internalStringState = remember { mutableStateOf(value.asString()) }

    if (internalStringState.value.toDuration() != value) {
        internalStringState.value = value.asString()
    }

    fun updateInternalStringState(newInternalStringState: String) {
        internalStringState.value = newInternalStringState
        onValueChange(newInternalStringState.toDuration())
    }

    fun enterDigit(char: Char): Boolean {
        return if (char.isDigit() && internalStringState.value.first() == '0') {
            updateInternalStringState(internalStringState.value.drop(1) + char)
            true
        } else {
            false
        }
    }

    fun removeDigit(): Boolean {
        updateInternalStringState('0' + internalStringState.value.dropLast(1))
        return true
    }

    @Composable
    fun formatInternalState(): String {
        val separator = ":"

        return StringBuilder().apply {
            append(internalStringState.value.subSequence(0, 2))
            append(separator)
            append(internalStringState.value.subSequence(2, 4))
            append(separator)
            append(internalStringState.value.subSequence(4, 6))
        }.toString()
    }

    val inputService = LocalTextInputService.current!!
    val textInputSession: MutableState<TextInputSession?> = remember { mutableStateOf(null) }
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    var focusRect: Rect? by remember { mutableStateOf(null) }

    if (isFocused && textInputSession.value == null) {
        textInputSession.value = inputService.startInput(
            value = TextFieldValue(),
            imeOptions = ImeOptions(
                singleLine = true,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            onEditCommand = { operations ->
                operations.forEach { operation ->
                    when (operation) {
                        is BackspaceCommand -> removeDigit()
                        is CommitTextCommand -> operation.text.forEach(::enterDigit)
                    }
                }
            },
            onImeActionPerformed = { action ->
                if (action == ImeAction.Done) {
                    focusRequester.freeFocus()
                }
            }
        )
    } else if (!isFocused && textInputSession.value != null) {
        textInputSession.value?.let(inputService::stopInput)
        textInputSession.value = null
    }

    DisposableEffect(focusRect, textInputSession.value) {
        val focusRectValue = focusRect
        val textInputSessionValue = textInputSession.value
        if (focusRectValue != null && textInputSessionValue != null) {
            textInputSessionValue.notifyFocusedRect(focusRectValue)
        }
        onDispose {}
    }

    BetterTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .focusable(interactionSource = interactionSource)
            .onKeyEvent {
                // FIXME(https://issuetracker.google.com/issues/188119984): Should keep only onEditCommand
                if (it.type != KeyEventType.KeyDown) return@onKeyEvent false
                if (it.key == Backspace) {
                    removeDigit()
                } else {
                    enterDigit(it.utf16CodePoint.toChar())
                }
            }
            .clickable { focusRequester.requestFocus() }
            .onGloballyPositioned { layoutCoordinates ->
                focusRect = layoutCoordinates.boundsInWindow()
            }
            .padding(8.dp),
        value = formatInternalState(),
        isError = state.isError,
        enabled = state.isEnabled,
        labelText = state.label,
        errorText = state.error,
        readOnly = true,
        interactionSource = interactionSource,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
    ) {}
}

private val TWO_DIGITS_FORMAT = DecimalFormat("00")
private fun Int.twoDigits() = TWO_DIGITS_FORMAT.format(this)

private const val SECONDS_PER_MINUTE = 60L
private const val MINUTES_PER_HOUR = 60L

@Preview
@Composable
private fun Preview() {
    val duration: InputFieldState<Duration> = InputFieldState(Duration.ZERO, label = "Duration")
    PablosFitnessTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PabloDurationPicker(state = duration) {

            }
        }
    }
}