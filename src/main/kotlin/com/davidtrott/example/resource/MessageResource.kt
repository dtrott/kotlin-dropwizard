package com.davidtrott.example.resource

import com.codahale.metrics.annotation.Timed
import com.davidtrott.example.database.model.MessageBody
import com.davidtrott.example.service.MessageService
import io.dropwizard.hibernate.UnitOfWork
import java.util.*
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
class MessageResource @Inject constructor(
    private val messageService: MessageService,
) {

    @GET // Using get, so I can test from the browser this should be a POST
    @Timed
    @UnitOfWork // All DB operations need unit of work
    @Path("store")
    fun store() = messageService.store("Some Text", MessageBody("Alpha", "Beta"))

    @GET
    @Timed
    @Path("{id}")
    @UnitOfWork // All DB operations need unit of work
    fun load(@PathParam("id") id: UUID) = messageService.load(id)
}
