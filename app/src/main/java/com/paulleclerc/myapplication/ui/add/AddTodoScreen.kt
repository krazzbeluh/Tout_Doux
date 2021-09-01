package com.paulleclerc.myapplication.ui.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.paulleclerc.myapplication.ViewTags.AddConfirmed
import com.paulleclerc.myapplication.ViewTags.AddTitleTextField
import com.paulleclerc.myapplication.util.ext.testTag
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@Composable
fun AddTodoScreen(viewModel: AddTodoViewModel = getViewModel(), onConfirm: () -> Unit) {
    var titleText by remember { mutableStateOf("") }
    var subtitleText by remember { mutableStateOf("") }
    val dateText by viewModel.dateText.collectAsState()
    val errors by viewModel.errors.collectAsState()

    val dialog = remember { MaterialDialog() }
    dialog.build(buttons = {
        positiveButton("Ok")
        negativeButton("Annuler") {
            viewModel.setSelectedDate(null)
        }
    }) {
        datepicker { localDate ->
            viewModel.setSelectedDate(localDate)
        }
    }

    LaunchedEffect(key1 = "key") {
        viewModel.navigateToList.onEach {
            onConfirm()
        }.collect()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val requester = FocusRequester()
        TextField(
            modifier = Modifier
                .focusRequester(requester)
                .onFocusChanged { state ->
                    if (!state.isFocused) viewModel.clearErrors()
                }
                .testTag(AddTitleTextField),
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Titre") },
            isError = errors.isNotEmpty()
        )
        TextField(
            value = subtitleText,
            onValueChange = { subtitleText = it },
            label = { Text("Sous titre") }
        )
        TextField(
            value = dateText,
            onValueChange = {},
            label = { Text("Date") },
            readOnly = true,
            enabled = false,
            modifier = Modifier.clickable {
                dialog.show()
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
            )
        )
        Button(
            modifier = Modifier.testTag(AddConfirmed),
            onClick = {
                viewModel.addConfirmed(title = titleText, subtitle = subtitleText)
            }) {
            Text(text = "Ajouter")
        }
        SideEffect {
            if (errors.isNotEmpty()) requester.requestFocus()
        }
    }
}
