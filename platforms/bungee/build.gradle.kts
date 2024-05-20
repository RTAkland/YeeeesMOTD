val bungeeVersion: String by project
val pluginVersion: String by project
val bungeeAdventureVersion: String by project
val miniMessageVersion: String by project

base {
    archivesName = rootProject.name + ".bungeecord+$bungeeVersion"
}

dependencies {
    api(project(":core"))
    compileOnly("net.md-5:bungeecord-api:$bungeeVersion")
    implementation("net.kyori:adventure-platform-bungeecord:$bungeeAdventureVersion")
    implementation("net.kyori:adventure-text-minimessage:$miniMessageVersion")
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "返回个性化的MOTD"
    )
    inputs.properties(properties)
    filteringCharset = "UTF-8"
    filesMatching("bungee.yml") {
        expand(properties)
    }
}
