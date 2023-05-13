package com.davidtrott.example.resource

import com.codahale.metrics.annotation.Timed
import com.davidtrott.example.api.domain.v1.generated.apis.ExampleApi
import com.davidtrott.example.api.model.Saying
import com.davidtrott.example.init.ExampleConfiguration
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class ExampleResource @Inject constructor(
    private val config: ExampleConfiguration
) : ExampleApi {

    private val counter = AtomicLong()

    @Timed
    override fun getHelloWorld(name: String?): Response = Response.ok().entity(
        Saying(
            counter.incrementAndGet(),
            String.format(config.template, name ?: config.defaultName)
        )
    ).build()
}
