import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val pluginVersion: String by project

base {
    archivesName = archivesName.get().replace("+", "+bukkit")
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

tasks.compileKotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_21
}

tasks.compileJava {
    sourceCompatibility = "21"
    targetCompatibility = "21"
}

tasks.compileJava {
    sourceCompatibility = "21"
    targetCompatibility = "21"
}