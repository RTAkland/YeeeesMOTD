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
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
        "description" to "返回个性化的MOTD"
    )
    inputs.properties(properties)
    filteringCharset = "UTF-8"
    filesMatching("velocity-plugin.json") {
        expand(properties)
    }
}

tasks.runVelocity {
    velocityVersion(velocityVersion)
}

tasks.create("generateTemplates") {}
