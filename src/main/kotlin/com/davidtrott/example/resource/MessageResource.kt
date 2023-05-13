package com.davidtrott.example.resource

import com.codahale.metrics.annotation.Timed
import com.davidtrott.example.api.domain.v1.generated.apis.MessageApi
import com.davidtrott.example.api.domain.v1.generated.models.Message
import com.davidtrott.example.api.domain.v1.generated.models.MessageBody
import com.davidtrott.example.api.model.ApplicationResult.Companion.toResponse
import com.davidtrott.example.service.MessageService
import com.davidtrott.example.service.MessageService.FailedToLoad
import io.dropwizard.hibernate.UnitOfWork
import java.util.*
import javax.inject.Inject
import javax.ws.rs.core.Response
import com.davidtrott.example.database.model.Message as MessageApp
import com.davidtrott.example.database.model.MessageBody as MessageBodyApp

class MessageResource @Inject constructor(
    private val messageService: MessageService,
) : MessageApi {

    @Timed
    @UnitOfWork // All DB operations need unit of work
    override fun getMessage(id: UUID): Response = messageService
        .load(id)
        .map { it.toWeb() }
        .toResponse(::handleFailedToLoadError)


    @Timed
    @UnitOfWork // All DB operations need unit of work
    override fun storeMessage(): Response = messageService
        .store("Some Text", MessageBodyApp("Alpha", "Beta"))
        .toResponse()


    // Error handling

    private fun handleFailedToLoadError(error: FailedToLoad): Response = when (error) {
        FailedToLoad.NOT_FOUND -> Response.status(404)
    }.build()


    // Extensions to convert type from application to web
    private fun MessageApp.toWeb() = Message(text, body.toWeb())

    private fun MessageBodyApp.toWeb() = MessageBody(valueA, valueB)
}
