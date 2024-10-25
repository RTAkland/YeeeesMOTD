pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "YeeeesMOTD"

include(
    ":core",
    ":platforms:velocity",
    ":platforms:paper",
    ":platforms:spigot",
    ":platforms:bungee",
    ":platforms:fabric"
)
