package com.paulleclerc.myapplication

import com.paulleclerc.myapplication.database.TodoDatabase
import com.paulleclerc.myapplication.database.dao.TodoDao
import com.paulleclerc.myapplication.database.entities.TodoEntity
import com.paulleclerc.myapplication.model.Todo
import com.paulleclerc.myapplication.repository.TodoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import java.time.LocalDate
import kotlin.test.assertEquals

class TodoRepositoryTest : KoinTest {
    private lateinit var todoRepository: TodoRepository
    private lateinit var database: TodoDatabase
    private lateinit var todoDao: TodoDao

    private val todoDemo = TodoEntity(0, "TestTodo", "supTest", LocalDate.now(), false)

    @Before
    fun setUp() {
        database = mockk()
        todoDao = mockk()
        every { database.todoDao() } returns todoDao

        startKoin {
            modules(module {
                single { database }
            })
        }

        todoRepository = TodoRepository(get())
    }

    @Test
    fun getAllReturnsDatabaseData() {
        every { todoDao.getAll() } returns flow {
            this.emit(listOf(todoDemo))
        }

        val list = runBlocking {
            todoRepository.getAll().first()
        }

        assertEquals(1, list.count())
        val first = list.first()
        assertEquals(first, todoDemo)
    }

    @Test
    fun createCallsInsert() {
        every { todoDao.insert(any()) } returns Unit
        runBlocking {
            todoRepository.create(
                Todo(
                    todoDemo.id,
                    todoDemo.text,
                    todoDemo.subText,
                    todoDemo.dueDate,
                    todoDemo.done
                )
            )
        }

        verify { todoDao.insert(any()) }
    }

    @Test
    fun updateCallsUpdateFromDB() {
        every { todoDao.update(any()) } returns
        todoRepository.update(
            Todo(
                todoDemo.id,
                todoDemo.text,
                todoDemo.subText,
                todoDemo.dueDate,
                todoDemo.done
            )
        )
        verify { todoDao.update(any()) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
