pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "YeeeesMOTD"

include(":core")
include(":platforms:velocity")
include(":platforms:fabric")
