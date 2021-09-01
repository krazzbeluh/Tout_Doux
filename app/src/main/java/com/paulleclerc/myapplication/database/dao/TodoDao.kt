package com.paulleclerc.myapplication.database.dao

import androidx.room.*
import com.paulleclerc.myapplication.database.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("select * from TodoEntity")
    fun getAll(): Flow<List<TodoEntity>>

    @Insert
    fun insert(todo: TodoEntity)

    @Update
    fun update(todo: TodoEntity)
}