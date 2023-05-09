package com.davidtrott.example.database.model

import com.davidtrott.example.util.PlatformType

class Message {

    var id = PlatformType.nullUuid

    var text: String = ""

    lateinit var body: MessageBody
}
