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

tasks.register<Copy>("collectJars") {
    group = "build"
    val outputDir = layout.buildDirectory.dir("libs")
    into(outputDir)
    subprojects.forEach { subproject ->
        dependsOn(subproject.tasks.named("build"))
        val jarTask = subproject.tasks.findByName("jar")
        if (jarTask != null) {
            from(subproject.layout.buildDirectory.dir("libs"))
        }
    }
}

tasks.build {
    dependsOn(tasks.named("collectJars"))
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

    base {
        archivesName = rootProject.name + "-${project.name}+.${getCommitID()}"
    }
}
