package com.davidtrott.example.service

import com.davidtrott.example.api.model.ApplicationResult
import com.davidtrott.example.api.model.ApplicationResult.Failed
import com.davidtrott.example.api.model.ApplicationResult.Success
import com.davidtrott.example.database.dao.MessageDao
import com.davidtrott.example.database.model.Message
import com.davidtrott.example.database.model.MessageBody
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional

/**
 * This "service" demonstrates how commands can access the database.
 * The @Transactional annotation only works for web requests.
 * When a transactional method is called from a command it is necessary for the caller to start the transaction.
 * Optionally using the inTransaction() helper.
 */
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

    @Transactional
    fun store(text: String, body: MessageBody): ApplicationResult<UUID, Void, Void> =
        Success(messageDao.store(Message().apply {
            this.text = text
            this.body = body
        }))

    enum class FailedToLoad {
        NOT_FOUND
    }
}
