package com.hyperborge.pablosfitness.presentation.components

import android.content.res.Configuration
import coil.compose.AsyncImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PabloSearch() {
    var searchQuery by remember { mutableStateOf("plan") }

    fun onSearch() {
        /*TODO*/
    }

    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier,
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit,
        onImeSearch: () -> Unit,
    ) {
        Box(modifier = modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.surface,
                    ),
                    shape = RoundedCornerShape(100),
                    placeholder = { Text("Search...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            modifier = Modifier.minimumInteractiveComponentSize(),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize(),
                    value = searchQuery,
                    singleLine = true,
                    keyboardActions = KeyboardActions(onSearch = { onImeSearch() }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    onValueChange = onSearchQueryChange,
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = searchQuery.isNotBlank(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun MessageItem(
        onClick: () -> Unit,
        recipient: String,
        snippet: String,
        snippetHighlight: String,
        timestamp: String,
        image: String
    ) {
        Surface(
            onClick = onClick,
            color = Color.Transparent,
            modifier = Modifier.clip(MaterialTheme.shapes.medium)
        ) {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    overlineColor = Color.Transparent
                ),
                headlineContent = { Text(recipient) },
                supportingContent = {
                    val snippetString = buildAnnotatedString {
                        append(snippet)
                        val start = snippet.lowercase().indexOf(snippetHighlight.lowercase())
                        if (start != -1) {
                            val end = start + snippetHighlight.length
                            addStyle(
                                style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                                start = start,
                                end = end
                            )
                        }
                    }
                    Text(snippetString, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                trailingContent = { Text(timestamp) },
                leadingContent = {
                    AsyncImage(
                        model = image,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Chat photo",
                    )
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Scaffold(
            modifier = Modifier.widthIn(max = 600.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            topBar = {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    onImeSearch = { onSearch() }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Box(modifier = Modifier.padding(padding)) {
                    LazyColumn {
                        item {
                            Text(
                                text = "Messages",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                        item {
                            MessageItem(
                                onClick = { /*TODO */ },
                                recipient = "Emily Green",
                                image = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=688&q=80",
                                snippet = "Any plans for the weekend?",
                                snippetHighlight = searchQuery,
                                timestamp = "1m"
                            )
                        }
                        item {
                            MessageItem(
                                onClick = { /*TODO */ },
                                recipient = "Stew Wonder",
                                image = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=774&q=80",
                                snippet = "Sounds like a plan 👌",
                                snippetHighlight = searchQuery,
                                timestamp = "12m"
                            )
                        }
                        item {
                            MessageItem(
                                onClick = { /*TODO */ },
                                recipient = "Mark Thompson",
                                image = "https://images.unsplash.com/photo-1552374196-c4e7ffc6e126?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=774&q=80",
                                snippet = " It's part of my 'cooking plans'",
                                snippetHighlight = searchQuery,
                                timestamp = "Fri"
                            )
                        }
                        item {
                            MessageItem(
                                onClick = { /*TODO */ },
                                recipient = "Stella Spanakopita",
                                image = "https://images.unsplash.com/photo-1535468850893-d6e543fbd7f5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1740&q=80",
                                snippet = "Plan it. I'll be there!",
                                snippetHighlight = searchQuery,
                                timestamp = "Sat"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            PabloSearch()
        }
    }
}