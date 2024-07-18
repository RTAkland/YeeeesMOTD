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

subprojects {
    group = "cn.rtast"
    version = pluginVersion

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
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

    tasks.compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    tasks.compileJava {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    tasks.jar {
        enabled = false
    }

    base {
        archivesName = rootProject.name + "-${project.name}+.${getCommitID()}"
    }
}
