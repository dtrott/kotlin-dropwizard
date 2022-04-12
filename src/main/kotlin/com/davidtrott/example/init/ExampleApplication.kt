package com.davidtrott.example.init

import com.davidtrott.example.healthcheck.TemplateHealthCheck
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class ExampleApplication : Application<ExampleConfiguration>() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) = ExampleApplication().run(*args)
    }

    override fun getName() = "Example"

    override fun initialize(bootstrap: Bootstrap<ExampleConfiguration>) {
        bootstrap.objectMapper.apply {
            registerKotlinModule()
        }
    }

    override fun run(configuration: ExampleConfiguration, environment: Environment) {
        val healthCheck = TemplateHealthCheck(configuration.template)

        environment.healthChecks().register("template", healthCheck)
    }
}