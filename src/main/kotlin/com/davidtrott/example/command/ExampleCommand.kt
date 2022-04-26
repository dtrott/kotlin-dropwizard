package com.davidtrott.example.command

import com.davidtrott.example.database.model.MessageBody
import com.davidtrott.example.database.util.inTransaction
import com.davidtrott.example.database.util.withSession
import com.davidtrott.example.init.ExampleConfiguration
import com.davidtrott.example.service.MessageService
import io.dropwizard.Application
import io.dropwizard.cli.EnvironmentCommand
import io.dropwizard.setup.Environment
import net.sourceforge.argparse4j.inf.Namespace
import net.sourceforge.argparse4j.inf.Subparser
import org.hibernate.SessionFactory
import javax.inject.Inject

/**
 * Example command.
 *
 * Typically, this would be invoked with arguments:
 * java ... exampleCommand config.yml -m Hello
 */
// I would like to use ExampleCommand(application: ExampleApplication) here,
// but Dropwizard won't find the correct constructor, if defined that way.
class ExampleCommand(application: Application<ExampleConfiguration>) : EnvironmentCommand<ExampleConfiguration>(
    application,
    "exampleCommand",
    "Example command"
) {

    @Inject
    lateinit var sessionFactory: SessionFactory

    @Inject
    lateinit var messageService: MessageService

    // Force the caller to provide a message on the command line
    override fun configure(subparser: Subparser) {
        super.configure(subparser)

        subparser.addArgument("-m", "--message")
            .dest("message")
            .type(String::class.java)
            .required(true)
            .help("The message")
    }

    override fun run(environment: Environment, namespace: Namespace, config: ExampleConfiguration) {
        val id = sessionFactory.withSession {
            sessionFactory.inTransaction {
                messageService.store(namespace.getString("message"), MessageBody("One", "Two"))
            }
        }

        println(id)
    }
}