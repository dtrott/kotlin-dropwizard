package com.davidtrott.example.database.model

import com.davidtrott.example.util.PlatformType
import org.jdbi.v3.core.mapper.Nested

class Message {

    var id = PlatformType.nullUuid

    var text: String = ""

    @Nested("body")
    lateinit var body: MessageBody
}
