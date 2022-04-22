package com.davidtrott.example.database.model

import javax.persistence.Column
import javax.persistence.Embeddable

// In Java, we can define "Domain Objects" as "Read-Only" by not providing any setters.
// Hibernate will directly update the backing fields for such an object.
// But there is no clean way to prevent setter generation in kotlin (given hibernates requirements), so...
@Embeddable
// The AllOpen plugin will open the class anyway.
// The primary advantage of declaring this as a data class is the auto generated methods:
// - equals
// - hashcode
// - copy
// - ... etc
data class MessageBody(
    // We can't declare the fields as "val" since the underlying fields will be final at the JVM level.
    // Which "may" interfere with hibernate operation.
    @Column(nullable = false)
    var valueA: String,


    @Column(nullable = false)
    var valueB: String,
) {

    // Needed for hibernate - marked as protected to avoid accidental use
    protected constructor() : this("", "")
}
