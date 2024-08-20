pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "YeeeesMOTD"

include(
    ":core",
    ":platforms:velocity",
    ":platforms:paper",
    ":platforms:spigot",
    ":platforms:bungee",
)
