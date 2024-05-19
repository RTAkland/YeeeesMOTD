plugins {
    id("java")
    kotlin("jvm") version ("1.9.24")
    id("com.github.johnrengelman.shadow") version ("8.1.1")
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
    }
}

val pluginVersion: String by project

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

        from("$rootDir/LICENSE")
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}

tasks.jar {
    enabled = false
}