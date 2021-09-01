package com.paulleclerc.myapplication.ui.add

import androidx.lifecycle.ViewModel
import com.paulleclerc.myapplication.model.Todo
import com.paulleclerc.myapplication.repository.TodoRepository
import com.paulleclerc.myapplication.util.format
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddTodoViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {
    private val coroutineScope = MainScope()

    private val _navigateToList = Channel<Unit>(Channel.BUFFERED)
    val navigateToList = _navigateToList.receiveAsFlow()

    private val _dateText = MutableStateFlow("")
    val dateText = _dateText.asStateFlow()

    private var date: LocalDate? = null

    private val _errors = MutableStateFlow(listOf<Error>())
    val errors = _errors.asStateFlow()

    fun addConfirmed(title: String, subtitle: String) {
        val errors = mutableListOf<Error>()
        if (title == "") errors.add(Error.MissingTitleValue)
        if (errors.isNotEmpty()) {
            this._errors.value = errors
            return
        }

        TodoRepository.coroutineScope.launch {
            todoRepository.create(Todo(0, title, subtitle, date, false))
            coroutineScope.launch {
                _navigateToList.send(Unit)
            }
        }

    }

    fun setSelectedDate(localDate: LocalDate?) {
        date = localDate
        _dateText.value = localDate?.format() ?: ""
    }

    fun clearErrors() {
        _errors.value = listOf()
    }

    enum class Error {
        MissingTitleValue
    }
}
