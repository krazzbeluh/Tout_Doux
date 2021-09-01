package com.paulleclerc.myapplication.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulleclerc.myapplication.R
import com.paulleclerc.myapplication.ViewTags
import com.paulleclerc.myapplication.ViewTags.*
import com.paulleclerc.myapplication.model.Todo
import com.paulleclerc.myapplication.util.ext.testTag
import com.paulleclerc.myapplication.util.format
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun ListTodosScreen(viewModel: ListTodosViewModel = getViewModel(), onConfirm: () -> Unit = {}) {
    val onConfirmClicked = { viewModel.onAddTodo() }

    val todosList by viewModel.todosList.collectAsState()

    LaunchedEffect(key1 = "key") {
        viewModel.navigateToAdd.onEach {
            onConfirm()
        }.collect()
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) { append("Tout Doux") }
                    },
                    textAlign = TextAlign.Center
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag(AddFAB),
                onClick = onConfirmClicked
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                    contentDescription = ""
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = if (todosList.isEmpty()) Center else TopCenter
        ) {
            if (todosList.isEmpty()) Text(
                text = "Aucun élément"
            )
            else LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                content = {
                    items(todosList, itemContent = { item ->
                        TodoItem(todo = item,
                            checked = item.done,
                            onCheckedChange = { checked ->
                                viewModel.onUpdate(item, checked)
                            })
                        if (item !== todosList.lastOrNull()) Spacer(modifier = Modifier.height(8.dp))
                    })
                })
        }

    }
}

@Preview
@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo = Todo(
        0,
        "Apprendre Jetpack Compose",
        "Nouvelle UI officielle d'Android",
        LocalDate.now(),
        false
    ),
    checked: Boolean = false,
    onCheckedChange: (checked: Boolean) -> Unit = {}
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                modifier = Modifier.testTag(TodoCheckBox),
                checked = checked,
                onCheckedChange = onCheckedChange
            )
            Column(modifier = Modifier.weight(2f)) {
                Text(text = todo.text)
                Text(text = todo.subText)
            }
            Text(todo.dueDate?.format() ?: "")
        }
    }
}
