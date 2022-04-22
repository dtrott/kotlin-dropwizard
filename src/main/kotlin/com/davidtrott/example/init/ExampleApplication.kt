package com.davidtrott.example.init

import com.davidtrott.example.healthcheck.TemplateHealthCheck
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.AbstractModule
import io.dropwizard.Application
import io.dropwizard.hibernate.ScanningHibernateBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.hibernate.SessionFactory
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
import org.hibernate.cfg.Configuration
import ru.vyarus.dropwizard.guice.GuiceBundle

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

        val hibernate =
            object : ScanningHibernateBundle<ExampleConfiguration>("com.davidtrott.example.database.model") {
                override fun getDataSourceFactory(configuration: ExampleConfiguration) = configuration.database

                override fun configure(configuration: Configuration) {
                    super.configure(configuration)

                    // Force snake case on the database fields
                    configuration.setPhysicalNamingStrategy(CamelCaseToUnderscoresNamingStrategy())
                }
            }

        with(bootstrap) {
            addBundle(hibernate)
            addBundle(
                GuiceBundle.builder()
                    .enableAutoConfig("com.davidtrott.example")
                    .modules(object : AbstractModule() {
                        override fun configure() =
                            bind(SessionFactory::class.java).toInstance(hibernate.sessionFactory)
                    })
                    .build()
            )
        }
    }

    override fun run(configuration: ExampleConfiguration, environment: Environment) {
        val healthCheck = TemplateHealthCheck(configuration.template)

        environment.healthChecks().register("template", healthCheck)
    }
}