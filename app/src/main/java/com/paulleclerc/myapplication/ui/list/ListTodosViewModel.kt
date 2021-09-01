package com.paulleclerc.myapplication.ui.list

import androidx.lifecycle.ViewModel
import com.paulleclerc.myapplication.model.Todo
import com.paulleclerc.myapplication.repository.TodoRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListTodosViewModel(
    private val todoRepository: TodoRepository,
): ViewModel() {
    init {
        TodoRepository.coroutineScope.launch {
            todoRepository.getAll().onEach {
                _todosList.emit(it.map { todoEntity ->
                    Todo(todoEntity.id, todoEntity.text, todoEntity.subText, todoEntity.dueDate, todoEntity.done)
                }.sorted())

            }.collect()
        }
    }

    private val _todosList = MutableStateFlow<List<Todo>>(listOf())
    val todosList = _todosList.asStateFlow()

    private val coroutineScope = MainScope()

    private val _navigateToAdd = Channel<Unit>(Channel.BUFFERED)
    val navigateToAdd = _navigateToAdd.receiveAsFlow()

    fun onAddTodo() {
        coroutineScope.launch {
            _navigateToAdd.send(Unit)
        }
    }

    fun onUpdate(item: Todo, checked: Boolean) {
        val newItem = Todo(item.id, item.text, item.subText, item.dueDate, checked)
        todoRepository.update(newItem)
    }
}