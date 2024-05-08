plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    kotlin("jvm") version "1.9.24"
}

val mod_version: String by project
val maven_group: String by project

val minecraft_version: String by project
val yarn_mappings: String by project
val loader_version: String by project
val fabric_version: String by project

val fabric_kotlin_version: String by project
val gson_version: String by project

val archives_base_name: String by project

base {
    archivesName = "$archives_base_name.fabric+$minecraft_version"
}

group = maven_group
version = mod_version

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings:v2")

    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabric_kotlin_version")

    implementation("com.google.code.gson:gson:$gson_version")

}

tasks.processResources {
    inputs.property("version", mod_version)

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to mod_version,
                "loader_version" to loader_version,
                "minecraft_version" to minecraft_version
            )
        )
    }
}

tasks.compileJava {
    targetCompatibility = "21"
    sourceCompatibility = "21"
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "21"
}

java {
    withSourcesJar()
}

tasks.jar {
    from("LICENSE")
}


