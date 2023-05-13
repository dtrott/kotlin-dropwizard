package com.davidtrott.example.database.dao

import com.davidtrott.example.database.model.Message
import java.util.*

interface MessageDao {
    fun findById(id: UUID): Message?

    fun store(apply: Message): UUID
}
