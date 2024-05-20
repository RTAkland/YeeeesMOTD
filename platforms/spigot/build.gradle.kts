val pluginVersion: String by project
val minecraftVersion: String by project

base {
    archivesName = rootProject.name + ".spigot+mc.$minecraftVersion"
}

dependencies {
    api(project(":core"))
    compileOnly(libs.spigot)
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "返回个性化的MOTD"
    )
    inputs.properties(properties)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(properties)
    }
}
