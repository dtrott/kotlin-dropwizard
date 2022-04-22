package com.davidtrott.example.database.util

import org.hibernate.dialect.PostgreSQL10Dialect
import java.sql.Types

class CustomPostgreSQLDialect : PostgreSQL10Dialect() {
    init {
        registerColumnType(Types.VARCHAR, "varchar")
    }
}
