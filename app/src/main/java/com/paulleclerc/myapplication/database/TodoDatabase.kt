package com.paulleclerc.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paulleclerc.myapplication.database.converter.DateConverter
import com.paulleclerc.myapplication.database.dao.TodoDao
import com.paulleclerc.myapplication.database.entities.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TodoDatabase::class.java, "todo-list.db"
        ).build()
    }
}