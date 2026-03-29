package com.example.peptalkgenerator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.R
import com.example.peptalkgenerator.data.Phrase
import com.example.peptalkgenerator.model.PHRASE_TYPES
import com.example.peptalkgenerator.model.PhrasesManagementViewModel
import com.example.peptalkgenerator.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ManageSayingsDestination : NavigationDestination {
    override val route = "manage_sayings"
    override val titleRes = R.string.manage_sayings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageSayingsScreen(
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    val viewModel: PhrasesManagementViewModel = viewModel(factory = PhrasesManagementViewModel.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(drawerState = drawerState) }
    ) { innerPadding ->
        ManageSayingsBody(
            phrasesList = uiState.phrasesList,
            newPhraseType = viewModel.newPhraseType,
            newPhraseSaying = viewModel.newPhraseSaying,
            onTypeChange = viewModel::updateNewPhraseType,
            onSayingChange = viewModel::updateNewPhraseSaying,
            onAddClick = {
                coroutineScope.launch { viewModel.addPhrase() }
            },
            onDeleteClick = { phrase ->
                coroutineScope.launch { viewModel.deletePhrase(phrase) }
            },
            onEditConfirm = { phrase ->
                coroutineScope.launch { viewModel.updatePhrase(phrase) }
            },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageSayingsBody(
    phrasesList: List<Phrase>,
    newPhraseType: String,
    newPhraseSaying: String,
    onTypeChange: (String) -> Unit,
    onSayingChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onDeleteClick: (Phrase) -> Unit,
    onEditConfirm: (Phrase) -> Unit,
    modifier: Modifier = Modifier
) {
    var filterText by rememberSaveable { mutableStateOf("") }
    var filterType by rememberSaveable { mutableStateOf("") } // empty = all types

    val filteredList = phrasesList.filter { phrase ->
        val matchesType = filterType.isEmpty() || phrase.type == filterType
        val matchesText = filterText.isBlank() || phrase.saying.contains(filterText, ignoreCase = true)
        matchesType && matchesText
    }

    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_Small))
    ) {
        AddPhraseForm(
            selectedType = newPhraseType,
            saying = newPhraseSaying,
            onTypeChange = onTypeChange,
            onSayingChange = onSayingChange,
            onAddClick = onAddClick,
            modifier = Modifier.fillMaxWidth()
        )
        FilterBar(
            filterText = filterText,
            filterType = filterType,
            onFilterTextChange = { filterText = it },
            onFilterTypeChange = { filterType = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        if (filteredList.isEmpty()) {
            Text(
                text = if (phrasesList.isEmpty()) stringResource(R.string.no_sayings)
                       else stringResource(R.string.no_sayings_matching),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                items(items = filteredList, key = { it.id }) { phrase ->
                    PhraseItem(
                        phrase = phrase,
                        onDeleteClick = onDeleteClick,
                        onEditConfirm = onEditConfirm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    filterText: String,
    filterType: String,
    onFilterTextChange: (String) -> Unit,
    onFilterTypeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val allTypesLabel = stringResource(R.string.filter_all_types)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = filterText,
                onValueChange = onFilterTextChange,
                label = { Text(stringResource(R.string.filter_search)) },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = if (filterType.isEmpty()) allTypesLabel else typeDisplayName(filterType),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.filter_type)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(allTypesLabel) },
                        onClick = {
                            onFilterTypeChange("")
                            expanded = false
                        }
                    )
                    PHRASE_TYPES.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(typeDisplayName(type)) },
                            onClick = {
                                onFilterTypeChange(type)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhraseForm(
    selectedType: String,
    saying: String,
    onTypeChange: (String) -> Unit,
    onSayingChange: (String) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_Small)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.add_new_saying),
                style = MaterialTheme.typography.titleMedium
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = typeDisplayName(selectedType),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.saying_type)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    PHRASE_TYPES.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(typeDisplayName(type)) },
                            onClick = {
                                onTypeChange(type)
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = saying,
                onValueChange = onSayingChange,
                label = { Text(stringResource(R.string.saying_text)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onAddClick,
                enabled = saying.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.button_saveCustomPhrase))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhraseItem(
    phrase: Phrase,
    onDeleteClick: (Phrase) -> Unit,
    onEditConfirm: (Phrase) -> Unit,
    modifier: Modifier = Modifier
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var editDialogShown by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_Small)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = typeDisplayName(phrase.type),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = phrase.saying,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = { editDialogShown = true }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_saying),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { deleteConfirmationRequired = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_saying),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteSayingConfirmationDialog(
            onConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(phrase)
            },
            onCancel = { deleteConfirmationRequired = false }
        )
    }

    if (editDialogShown) {
        EditSayingDialog(
            phrase = phrase,
            onConfirm = { updated ->
                editDialogShown = false
                onEditConfirm(updated)
            },
            onCancel = { editDialogShown = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditSayingDialog(
    phrase: Phrase,
    onConfirm: (Phrase) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var editedSaying by rememberSaveable { mutableStateOf(phrase.saying) }
    var editedType by rememberSaveable { mutableStateOf(phrase.type) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(stringResource(R.string.edit_saying)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = typeDisplayName(editedType),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.saying_type)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        PHRASE_TYPES.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(typeDisplayName(type)) },
                                onClick = {
                                    editedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = editedSaying,
                    onValueChange = { editedSaying = it },
                    label = { Text(stringResource(R.string.saying_text)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(phrase.copy(type = editedType, saying = editedSaying)) },
                enabled = editedSaying.isNotBlank()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    )
}

@Composable
private fun DeleteSayingConfirmationDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_saying_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.yes))
            }
        }
    )
}

fun typeDisplayName(type: String): String = when (type) {
    "greeting" -> "Intro"
    "first" -> "First Part"
    "second" -> "Second Part"
    "ending" -> "Ending"
    else -> type
}
