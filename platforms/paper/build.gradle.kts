plugins {
    alias(libs.plugins.runPaper)
}

val pluginVersion: String by project
val paperMinecraftVersion: String by project

base {
    archivesName = archivesName.get().replace("+", "")
}

repositories {
    mavenCentral()
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
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

runPaper.folia.registerTask()
