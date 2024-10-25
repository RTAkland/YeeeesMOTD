import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
}

val pluginVersion: String by project

fun getCommitID(): String {
    val command = if (System.getProperty("os.name").startsWith("Windows")) {
        arrayOf("cmd", "/c", "git log --pretty=format:%h -1")
    } else {
        arrayOf("sh", "-c", "git log --pretty=format:%h -1")
    }
    val process = ProcessBuilder()
        .command(*command)
        .start()
    return process.inputStream.bufferedReader().readLine()
}

tasks.jar {
    enabled = false
}

subprojects {
    group = "cn.rtast"
    version = pluginVersion

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.gradleup.shadow")
    }

    tasks.build {
        dependsOn(tasks.shadowJar)
    }

    tasks.shadowJar {
        exclude("com/google/gson/**")
        exclude("org/jetbrains/**")
        exclude("org/intellij/**")

        from("$rootDir/LICENSE")
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    tasks.compileJava {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    base {
        archivesName = rootProject.name + "-${project.name}+.${getCommitID()}"
    }
}
