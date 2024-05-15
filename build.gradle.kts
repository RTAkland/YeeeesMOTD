plugins {
    kotlin("jvm") version ("1.9.24")
    id("xyz.jpenilla.run-velocity") version ("2.3.0")
}

val gsonVersion: String by project
val kotlinVersion: String by project
val pluginVersion: String by project
val velocityVersion: String by project

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")

    implementation("com.google.code.gson:gson:${gsonVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
}

base {
    archivesName = project.name + ".velocity"
}

tasks.processResources {
    val properties = mapOf(
        "version" to pluginVersion,
    )
    inputs.properties(properties)
    filesMatching("velocity-plugin.json") {
        expand(properties)
    }
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.compileJava {
    targetCompatibility = "17"
    sourceCompatibility = "17"
    options.encoding = "UTF-8"
}

tasks.runVelocity {
    velocityVersion(velocityVersion)
}

tasks.jar {
    from("LICENSE")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val files = configurations.runtimeClasspath.get()
        .exclude("com.velocitypowered")
        .exclude("com.google.code.gson")
        .filter { it.exists() }
        .map { if (it.isDirectory) it else zipTree(it) }
    from(files)
}

tasks.create("generateTemplates") {}
