package com.davidtrott.example.api.model

import java.net.URI
import javax.ws.rs.core.Response

sealed class ApplicationResult<out D, out E, out R> {

    data class Success<D>(val entity: D) : ApplicationResult<D, Nothing, Nothing>()
    data class Failed<E>(val error: E) : ApplicationResult<Nothing, E, Nothing>()
    data class Redirect<R>(val redirectType: R) : ApplicationResult<Nothing, Nothing, R>()

    fun <D2> map(transform: (D) -> D2): ApplicationResult<D2, E, R> {
        return when (this) {
            is Success -> Success(transform(entity))
            is Failed -> this
            is Redirect -> this
        }
    }

    fun <E2> mapError(transform: (E) -> E2): ApplicationResult<D, E2, R> {
        return when (this) {
            is Success -> this
            is Failed -> Failed(transform(error))
            is Redirect -> this
        }
    }

    fun <R2> mapRedirect(transform: (R) -> R2): ApplicationResult<D, E, R2> {
        return when (this) {
            is Success -> this
            is Failed -> this
            is Redirect -> Redirect(transform(redirectType))
        }
    }

    fun toResponse(
        mapError: (E) -> Response,
        mapRedirect: (R) -> URI,
    ): Response = when (this) {
        is Success<D> -> Response.ok().entity(entity).build()
        is Failed<E> -> mapError(error)
        is Redirect<R> -> Response.temporaryRedirect(mapRedirect(redirectType)).build()
    }

    companion object {
        private val ROOT_URI: URI = URI.create("/")

        fun ApplicationResult<*, Void, Void>.toResponse() = toResponse(
            { Response.serverError().build() },
            { ROOT_URI },
        )

        fun <E> ApplicationResult<*, E, Void>.toResponse(mapError: (E) -> Response): Response =
            toResponse(mapError) { ROOT_URI }
    }
}
