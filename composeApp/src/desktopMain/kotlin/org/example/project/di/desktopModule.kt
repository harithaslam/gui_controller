package org.example.project.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.project.cache.Database
import com.example.project.cache.SensorData
import org.example.project.DataProcess
import org.koin.core.module.Module
import org.koin.dsl.module


val desktopModules : Module = module {
    single{
        // Create the SQLite driver
        val driver = JdbcSqliteDriver("jdbc:sqlite:sensorData.db").apply {
            execute(null, "PRAGMA busy_timeout = 30000", 0) // Set timeout to 30 seconds
        }

        // Initialize the database schema if it doesn't exist
        Database.Schema.create(driver)

        // Return the database instance
        Database(driver)

    }
    single {
        get<Database>().sensorDataQueries
    }

    single{
        DataProcess(get())
    }

}