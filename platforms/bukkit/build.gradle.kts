plugins {
    id("xyz.jpenilla.run-paper") version("2.3.0")
}

val pluginVersion: String by project
val paperVersion: String by project
val minecraftVersion: String by project

base {
    archivesName = rootProject.name + ".bukkit+$minecraftVersion"
}

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    api(project(":core"))
    compileOnly("io.papermc.paper:paper-api:$paperVersion")
    implementation(kotlin("stdlib"))
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion
    )
    inputs.properties(properties)
    filesMatching("plugin.yml") {
        expand(properties)
    }
}

tasks.runServer {
    minecraftVersion(minecraftVersion)
}


