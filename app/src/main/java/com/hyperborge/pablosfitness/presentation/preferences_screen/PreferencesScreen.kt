package com.hyperborge.pablosfitness.presentation.preferences_screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.presentation.components.PabloIcon
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Settings")
                },
                actions = {
                    IconButton(onClick = {}) {
                        PabloIcon(painter = painterResource(R.drawable.baseline_save_24))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        PabloIcon(imageVector = Icons.Default.ArrowBack)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            item {
                AnimatedSettingItem(
                    label = "Dark Mode",
                    description = "Enable dark mode for a better viewing experience",
                    isChecked = true,
                    onCheckedChange = { /* update shared preferences */ }
                )
            }
            item {
                AnimatedSettingItem(
                    label = "Notifications",
                    description = "Receive notifications for important updates",
                    isChecked = false,
                    onCheckedChange = { /* update shared preferences */ }
                )
            }
            item {
                AnimatedSettingItem(
                    label = "Font Size",
                    description = "Adjust the font size for better readability",
                    isChecked = false,
                    onCheckedChange = { /* update shared preferences */ }
                )
            }
            // add more settings items as needed
        }
    }
}

@Composable
fun AnimatedSettingItem(
    label: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    var switchState by remember { mutableStateOf(isChecked) }
    val transition = updateTransition(targetState = switchState, label = "transition")

    val backgroundTint by transition.animateColor(
        transitionSpec = {
            if (switchState) {
                tween(durationMillis = 300)
            } else {
                tween(durationMillis = 300, delayMillis = 150)
            }
        }, label = "backgroundTint"
    ) { state ->
        if (state) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        } else {
            MaterialTheme.colorScheme.background.copy(alpha = 0.12f)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(backgroundTint)
            .clickable { switchState = !switchState }
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 18.sp)
            Text(text = description, fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Surface(
            shape = CircleShape,
            color = if (switchState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
            tonalElevation = 4.dp,
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .graphicsLayer {
                        alpha = if (switchState) 1f else 0f
                        rotationZ = if (switchState) 0f else -90f
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        PreferencesScreen()

    }
}