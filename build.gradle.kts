plugins {
    id("java")
    kotlin("jvm") version ("1.9.24")
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

val pluginVersion: String by project
val javaVersion: String by project

val subProjectWithoutKotlinRuntime = listOf(":velocity")

subprojects {
    group = "cn.rtast"
    version = pluginVersion

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
    }

    tasks.build {
        dependsOn(tasks.shadowJar)
    }

    tasks.shadowJar {
        exclude("com/velocitypowered/**")
        exclude("com/google/gson/**")
        exclude("org/jetbrains/**")
        exclude("org/intellij/**")
    }

    tasks.compileKotlin {
        kotlinOptions.jvmTarget = javaVersion
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

tasks.jar {
    enabled = false
}
