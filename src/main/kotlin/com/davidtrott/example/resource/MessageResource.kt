package com.davidtrott.example.resource

import com.codahale.metrics.annotation.Timed
import com.davidtrott.example.database.dao.MessageDao
import com.davidtrott.example.database.model.Message
import com.davidtrott.example.database.model.MessageBody
import io.dropwizard.hibernate.UnitOfWork
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
class MessageResource @Inject constructor(
    private val messageDao: MessageDao,
) {

    @GET // Using get, so I can test from the browser this should be a POST
    @Timed
    @UnitOfWork // All DB operations need unit of work
    @Transactional // We are writing so we need transactional too
    @Path("store")
    fun store(): UUID =
        messageDao.store(Message().apply {
            text = "Some Text"
            body = MessageBody("Alpha", "Beta")
        }).id

    @GET
    @Timed
    @Path("{id}")
    @UnitOfWork // All DB operations need unit of work
    fun load(@PathParam("id") id: UUID): Message? = messageDao.findById(id)
}
