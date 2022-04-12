package com.davidtrott.example.init

import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

data class ExampleConfiguration(
    val template: @NotEmpty String,
    val defaultName: @NotEmpty String = "Stranger",
) : Configuration() {

    @Valid
    val database = DataSourceFactory()
}
