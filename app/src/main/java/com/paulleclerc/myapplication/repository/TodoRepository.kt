package com.paulleclerc.myapplication.repository

import com.paulleclerc.myapplication.database.TodoDatabase
import com.paulleclerc.myapplication.database.entities.TodoEntity
import com.paulleclerc.myapplication.model.Todo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent.get

class TodoRepository(private val db: TodoDatabase) {
    companion object {
        private val parentJob = Job()
        val coroutineScope = CoroutineScope(Dispatchers.Default + parentJob)
    }

    suspend fun create(todo: Todo) =
        withContext(coroutineScope.coroutineContext) {
            val todoEntity = TodoEntity(0, todo.text, todo.subText, todo.dueDate, todo.done)

            db.todoDao().insert(todoEntity)
        }

    suspend fun getAll(): Flow<List<TodoEntity>> =
        withContext(coroutineScope.coroutineContext) {
            db.todoDao().getAll()
        }

    fun update(item: Todo) {
        coroutineScope.launch {
            db.todoDao().update(item.asEntity)
        }
    }

    private val Todo.asEntity
        get() = TodoEntity(id, text, subText, dueDate, done)
}
