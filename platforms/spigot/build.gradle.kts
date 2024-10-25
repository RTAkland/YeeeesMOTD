val pluginVersion: String by project

base {
    archivesName = archivesName.get().replace("+", "+bukkit")
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
