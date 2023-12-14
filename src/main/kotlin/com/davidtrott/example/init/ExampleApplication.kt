package com.davidtrott.example.init

import com.davidtrott.example.healthcheck.TemplateHealthCheck
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Bootstrap
import io.dropwizard.core.setup.Environment
import org.jdbi.v3.core.kotlin.KotlinPlugin
import ru.vyarus.dropwizard.guice.GuiceBundle
import ru.vyarus.guicey.jdbi3.JdbiBundle

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

        with(bootstrap) {
            addBundle(
                GuiceBundle.builder()
                    .bundles(JdbiBundle
                        .forDatabase<ExampleConfiguration> { config, _ -> config.database }
                        .withConfig { jdbi -> jdbi.installPlugin(KotlinPlugin()) }
                    )
                    .enableAutoConfig("com.davidtrott.example")
                    .searchCommands()
                    .build()
            )
        }
    }

    override fun run(configuration: ExampleConfiguration, environment: Environment) {
        val healthCheck = TemplateHealthCheck(configuration.template)

        environment.healthChecks().register("template", healthCheck)
    }
}
