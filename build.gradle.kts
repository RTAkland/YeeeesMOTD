import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
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
}

val pluginVersion: String by project

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

    tasks.compileJava {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        options.encoding = "UTF-8"
    }

    tasks.compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}

tasks.jar {
    enabled = false
}