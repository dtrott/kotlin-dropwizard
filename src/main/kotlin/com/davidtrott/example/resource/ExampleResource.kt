package com.davidtrott.example.resource

import com.codahale.metrics.annotation.Timed
import com.davidtrott.example.api.model.Saying
import com.davidtrott.example.init.ExampleConfiguration
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
class ExampleResource @Inject constructor(
    private val config: ExampleConfiguration
) {

    private val counter = AtomicLong()

    @GET
    @Timed
    fun sayHello(@QueryParam("name") name: Optional<String>) = Saying(
        counter.incrementAndGet(),
        String.format(config.template, name.orElse(config.defaultName))
    )
}
