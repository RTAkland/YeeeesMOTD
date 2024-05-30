plugins {
    alias(libs.plugins.runPaper)
}

val pluginVersion: String by project
val paperMinecraftVersion: String by project

base {
    archivesName = rootProject.name + ".paper+mc.$paperMinecraftVersion"
}

dependencies {
    api(project(":core"))
    compileOnly(libs.paper)
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
    minecraftVersion(paperMinecraftVersion)
}


