package com.davidtrott.example.init

import io.dropwizard.Configuration
import javax.validation.constraints.NotEmpty

data class ExampleConfiguration(
    val template: @NotEmpty String,
    val defaultName: @NotEmpty String = "Stranger",
) : Configuration()
