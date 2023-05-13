package com.davidtrott.example.database.dao

import com.davidtrott.example.database.model.Message
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository
import ru.vyarus.guicey.jdbi3.tx.InTransaction
import java.util.*

@JdbiRepository
@InTransaction
interface MessageDao {

    @SqlQuery("SELECT * FROM message WHERE id = :id")
    fun findById(@Bind("id") id: UUID): Message?

    @SqlUpdate(
        """
        INSERT INTO message (id, text, body_valuea, body_valueb)
        VALUES (uuid_generate_v4(), :text, :body.valueA, :body.valueB)
        """
    )
    @GetGeneratedKeys("id")
    fun store(@BindBean apply: Message): UUID
}
