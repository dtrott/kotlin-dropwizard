package com.davidtrott.example.database.util

import org.hibernate.SessionFactory
import org.hibernate.context.internal.ManagedSessionContext

/**
 * Binds a session to the current "context" (thread) this is equivalent to the @UnitOfWork annotation
 */
fun <R> SessionFactory.withSession(block: () -> R): R {
    ManagedSessionContext.bind(openSession())

    return runCatching(block)
        .also { ManagedSessionContext.unbind(this) }
        .getOrThrow()
}

/**
 * Processes the block inside a transaction - equivalent of @Transactional
 *
 * If a method throws an exception this code will automatically rollback().
 */
fun <R> SessionFactory.inTransaction(block: () -> R) = with(currentSession.beginTransaction()) {
    runCatching(block)
        .onSuccess { commit() }
        .onFailure { rollback() }
        .getOrThrow()
}

