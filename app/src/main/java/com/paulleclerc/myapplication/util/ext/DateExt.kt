package com.paulleclerc.myapplication.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.format() = DateTimeFormatter.ofPattern("dd/MM/yyyy").let { this.format(it) } ?: ""