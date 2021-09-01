package com.paulleclerc.myapplication.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

object DateConverter {
    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate? = try {
        LocalDate.parse(dateString)
    } catch (e: DateTimeParseException) {
        null
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?) = date?.toString() ?: ""
}