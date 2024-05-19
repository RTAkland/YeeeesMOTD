plugins {
    id("xyz.jpenilla.run-velocity") version ("2.3.0")
}

val pluginVersion: String by project
val velocityVersion: String by project

base {
    archivesName = rootProject.name + ".velocity"
}

dependencies {
    api(project(":core"))
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "返回个性化的MOTD"
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
