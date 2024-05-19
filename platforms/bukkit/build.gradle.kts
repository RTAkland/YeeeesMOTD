plugins {
    id("xyz.jpenilla.run-paper") version("2.3.0")
}

val pluginVersion: String by project
val paperVersion: String by project
val minecraftVersion: String by project

base {
    archivesName = rootProject.name + ".bukkit+mc.$minecraftVersion"
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    compileOnly("io.papermc.paper:paper-api:$paperVersion")
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "Customize server motd and icon"
    )
    inputs.properties(properties)
    filesMatching("plugin.yml") {
        expand(properties)
    }
}

tasks.runServer {
    minecraftVersion(minecraftVersion)
}


