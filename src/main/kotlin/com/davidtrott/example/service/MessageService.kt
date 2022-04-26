package com.davidtrott.example.service

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
    fun load(id: UUID): Message? = messageDao.findById(id)

    @Transactional
    fun store(text: String, body: MessageBody): UUID =
        messageDao.store(Message().apply {
            this.text = text
            this.body = body
        }).id
}
