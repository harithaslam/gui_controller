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
        val driver = JdbcSqliteDriver("jdbc:sqlite:sensorData.db")

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