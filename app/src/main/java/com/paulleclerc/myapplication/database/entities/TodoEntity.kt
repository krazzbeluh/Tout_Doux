package com.paulleclerc.myapplication.database.entities

import android.media.audiofx.Equalizer
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val text: String,
    val subText: String,
    val dueDate: LocalDate?,
    val done: Boolean


) {
    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + subText.hashCode()
        result = 31 * result + (dueDate?.hashCode() ?: 0)
        result = 31 * result + done.hashCode()
        return result
    }
}
