package com.hyperborge.pablosfitness.presentation.exercises_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseItem(
    modifier: Modifier = Modifier,
    exercise: ExercisePresentationModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier.clickable {
            onClick()
        },
        headlineContent = {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingContent = {
            var moreExpanded by remember { mutableStateOf(false) }
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {
                    moreExpanded = moreExpanded.not()
                }
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = moreExpanded,
                onDismissRequest = { moreExpanded = false }
            ) {
                DropdownMenuItem(text = { Text(text = "Edit") }, onClick = {
                    moreExpanded = false
                    onEdit()
                })
                DropdownMenuItem(text = { Text(text = "Delete") }, onClick = {
                    moreExpanded = false
                    onDelete()
                })
            }
        }
    )
}