package com.hyperborge.pablosfitness.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.hyperborge.pablosfitness.presentation.util.InputFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PabloDropDown(
    modifier: Modifier = Modifier,
    selectedItem: InputFieldState<String>,
    items: List<T>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    menuItemBuilder: @Composable ((T) -> Unit)
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(it) },
    ) {
        BetterTextField(
            inputState = selectedItem,
            modifier = modifier.menuAnchor(),
            readOnly = true,
            onValueChange = {},
            visualTransformation = visualTransformation,
            textStyle = textStyle,
            singleLine = singleLine,
            maxLines = maxLines,
            leadingIcon = leadingIcon,
            colors = colors,
            shape = shape,
            interactionSource = interactionSource,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            items.forEach { item ->
                menuItemBuilder(item)
            }
        }
    }
}