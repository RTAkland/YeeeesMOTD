plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
}

val modVersion: String by project
val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project
val fabricVersion: String by project

version = modVersion
group = "cn.rtast"

base {
    archivesName = rootProject.name + ".fabric+$minecraftVersion"
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
}

//processResources {
//    inputs.property "version", modVersion
//    inputs.property "minecraft_version", minecraftVersion
//    inputs.property "loader_version", loaderVersion
//    filteringCharset "UTF-8"
//
//    filesMatching("fabric.mod.json") {
//        expand "version": modVersion,
//                "minecraft_version": minecraftVersion,
//                "loader_version": loaderVersion
//    }
//}
//
//def targetJavaVersion = 21
//tasks.withType(JavaCompile).configureEach {
//    it.options.encoding = "UTF-8"
//    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
//        it.options.release.set(targetJavaVersion)
//    }
//}
//
//java {
//    def javaVersion = JavaVersion.toVersion(targetJavaVersion)
//    if (JavaVersion.current() < javaVersion) {
//        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
//    }
//    withSourcesJar()
//}