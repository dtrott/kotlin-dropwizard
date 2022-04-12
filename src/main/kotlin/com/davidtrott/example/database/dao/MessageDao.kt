package com.davidtrott.example.database.dao

import com.davidtrott.example.database.model.Message
import org.hibernate.SessionFactory
import java.util.*
import javax.inject.Inject

class MessageDao @Inject constructor(sessionFactory: SessionFactory) :
    AbstractCrudDao<Message, UUID>(sessionFactory) {
}
