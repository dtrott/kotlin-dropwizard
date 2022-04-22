package com.davidtrott.example.database.model

import com.davidtrott.example.util.PlatformType
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Message {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id = PlatformType.nullUuid

    @Column(nullable = false)
    var text: String = ""

    @Embedded
    lateinit var body: MessageBody
}