plugins {
    alias(libs.plugins.runPaper)
}

val pluginVersion: String by project
val minecraftMajorVersion: String by project
val paperMinecraftVersion: String by project

base {
    archivesName = rootProject.name + ".paper+mc.$minecraftMajorVersion"
}

dependencies {
    api(project(":core"))
    compileOnly(libs.paper)
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "Customize server motd and icon",
        "majorVersion" to minecraftMajorVersion
    )
    inputs.properties(properties)
    filesMatching("plugin.yml") {
        expand(properties)
    }
}

tasks.runServer {
    minecraftVersion(paperMinecraftVersion)
}


