plugins {
    id("java")
    id("eclipse")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.0.1"
    kotlin("jvm") version "1.9.24"
    id("xyz.jpenilla.run-velocity") version "2.3.0"
}

val gson_version: String by project
val plugin_version: String by project

group = "cn.rtast"
version = plugin_version


repositories {
    gradlePluginPortal()
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}


dependencies {
    implementation("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")

    implementation("com.google.code.gson:gson:$gson_version")
    implementation(kotlin("stdlib"))

}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

val templateSource = file("src/main/templates")
val templateDest = layout.buildDirectory.dir("generated/sources/templates")

sourceSets {
    main {
        java {
            srcDirs(templateSource)
        }
    }
}

val generateTemplates = tasks.register<Copy>("generateTemplates") {
    val props = mapOf(
        "version" to plugin_version
    )
    inputs.properties(props)

    from(templateSource)
    into(templateDest)
    expand(props)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val files = configurations.runtimeClasspath.get()
        .exclude("com.google.code.gson")
        .exclude("com.velocitypowered")
        .filter { it.exists() }
        .map { if (it.isDirectory) it else zipTree(it) }
    from(files)
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.runVelocity {
    velocityVersion("3.3.0-SNAPSHOT")
}