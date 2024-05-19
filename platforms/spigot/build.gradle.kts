val pluginVersion: String by project
val paperVersion: String by project
val minecraftVersion: String by project

base {
    archivesName = rootProject.name + ".spigot+mc.$minecraftVersion"
}

dependencies {
    api(project(":core"))
    compileOnly("org.spigotmc:spigot-api:$paperVersion")
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "Customize server motd"
    )
    inputs.properties(properties)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(properties)
    }
}
