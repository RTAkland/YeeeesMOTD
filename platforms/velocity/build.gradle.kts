plugins {
    id("xyz.jpenilla.run-velocity") version ("2.3.0")
}

val pluginVersion: String by project
val velocityVersion: String by project

base {
    archivesName = rootProject.name + ".velocity"
}

repositories {
    maven {
        name = "PaperMc"
        setUrl("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    api(project(":core"))
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")

    implementation(kotlin("stdlib"))

}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
    )
    inputs.properties(properties)
    filesMatching("velocity-plugin.json") {
        expand(properties)
    }
}

tasks.runVelocity {
    velocityVersion(velocityVersion)
}

tasks.create("generateTemplates") {}

