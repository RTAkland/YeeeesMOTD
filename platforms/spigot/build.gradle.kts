val pluginVersion: String by project
val minecraftMajorVersion: String by project

base {
    archivesName = archivesName.get()
        .replace("+", "+bukkit+mc.$minecraftMajorVersion")
}

dependencies {
    api(project(":core"))
    compileOnly(libs.spigot)
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "返回个性化的MOTD",
        "majorVersion" to minecraftMajorVersion
    )
    inputs.properties(properties)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(properties)
    }
}
