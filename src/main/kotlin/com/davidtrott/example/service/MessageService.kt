package com.davidtrott.example.service

import com.davidtrott.example.api.model.ApplicationResult
import com.davidtrott.example.api.model.ApplicationResult.Failed
import com.davidtrott.example.api.model.ApplicationResult.Success
import com.davidtrott.example.database.dao.MessageDao
import com.davidtrott.example.database.model.Message
import com.davidtrott.example.database.model.MessageBody
import ru.vyarus.guicey.jdbi3.tx.InTransaction
import java.util.*
import javax.inject.Inject

@InTransaction
class MessageService @Inject constructor(
    private val messageDao: MessageDao,
) {
    fun load(id: UUID): ApplicationResult<Message, FailedToLoad, Void> =
        with(messageDao.findById(id)) {
            when (this) {
                null -> Failed(FailedToLoad.NOT_FOUND)
                else -> Success(this)
            }
        }

    fun store(text: String, body: MessageBody): ApplicationResult<UUID, Void, Void> =
        Success(messageDao.store(Message().apply {
            this.text = text
            this.body = body
        }))

    enum class FailedToLoad {
        NOT_FOUND
    }
}
