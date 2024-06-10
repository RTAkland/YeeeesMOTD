plugins {
    alias(libs.plugins.runVelocity)
}

val pluginVersion: String by project
val velocityVersion: String by project

base {
    archivesName = archivesName.get().replace("+", "")
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
