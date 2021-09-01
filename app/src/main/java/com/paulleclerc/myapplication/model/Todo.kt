package com.paulleclerc.myapplication.model

import java.time.LocalDate

data class Todo(
    val id: Long,
    val text: String,
    val subText: String,
    val dueDate: LocalDate?,
    val done: Boolean
) : Comparable<Todo> {
    override fun compareTo(other: Todo) =
        if (this.done) {
            if (other.done) {
                if (this.dueDate != null) {
                    if (other.dueDate != null) {
                        if (this.dueDate == other.dueDate) this.text.compareTo(other.text)
                        else this.dueDate.compareTo(other.dueDate)
                    } else -1
                } else if (other.dueDate != null) {
                    1
                } else this.text.compareTo(other.text)
            } else 1
        } else {
            if (other.done) -1
            else if (this.dueDate != null) {
                if (other.dueDate != null) {
                    if (this.dueDate == other.dueDate) this.text.compareTo(other.text)
                    else this.dueDate.compareTo(other.dueDate)
                } else -1
            } else if (other.dueDate != null) {
                1
            } else this.text.compareTo(other.text)
        }

}
