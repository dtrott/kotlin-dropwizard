package com.davidtrott.example.database.dao

import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import java.io.Serializable
import javax.inject.Inject

/**
 * Generic DAO providing standard methods to other specific DAOs.
 *
 * @param E the type of the entity this DAO will fetch/persist
 * @param K the type of the primary key of the entity
 */
// The Entity type (E) must be the first generic parameter or Hibernate will be very unhappy.
abstract class AbstractCrudDao<E : Any, K : Serializable> @Inject constructor(sessionFactory: SessionFactory) :
    AbstractDAO<E>(sessionFactory) {

    fun findById(id: K) = get(id)

    fun store(entity: E): E = persist(entity)
}
