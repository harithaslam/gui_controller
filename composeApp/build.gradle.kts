import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.0"
    id("app.cash.sqldelight") version "2.0.0"

}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
            implementation("io.insert-koin:koin-core:4.0.0-RC1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
            implementation("org.apache.poi:poi:5.2.3")
            implementation("org.apache.poi:poi-ooxml:5.2.3")
            implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:latest.release")
            implementation("io.github.koalaplot:koalaplot-core:0.6.3")
        }
        desktopMain.dependencies {
            implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
            implementation("com.fazecast:jSerialComm:2.11.0")
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}
sqldelight {
    databases {
        create("Database") {
            packageName.set("com.example.project.cache")
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"
        nativeDistributions {
            modules("java.instrument", "java.security.jgss", "java.sql", "java.xml.crypto", "jdk.unsupported")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb,TargetFormat.Exe)
            packageName = "GUI Controller"
            packageVersion = "1.0.0"
        }
    }
}
