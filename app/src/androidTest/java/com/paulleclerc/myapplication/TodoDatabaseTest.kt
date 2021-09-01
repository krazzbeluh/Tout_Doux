package com.paulleclerc.myapplication

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.paulleclerc.myapplication.database.TodoDatabase
import com.paulleclerc.myapplication.database.entities.TodoEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class TodoDatabaseTest {
    private lateinit var database: TodoDatabase

    private val todoDemo = TodoEntity(0, "TestTodo", "supTest", LocalDate.now(), false)

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(
            context,
            TodoDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun getAllReturnsFlowContainingAllElements() {
        var isFinished: Boolean
        database.todoDao().apply {
            insert(todoDemo)
            runBlocking {
                val first = getAll().first().first()
                assert(first.text == todoDemo.text)
                assert(first.subText == todoDemo.subText)
                assertEquals(first.done, todoDemo.done)
                assertEquals(first.dueDate, todoDemo.dueDate)
                isFinished = true
            }
        }
        assert(isFinished)
    }

    @After
    fun tearDown() {
        database.close()
    }
}
