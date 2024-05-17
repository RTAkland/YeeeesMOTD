plugins {
    id("fabric-loom") version("1.6-SNAPSHOT")
}

val pluginVersion: String by project
val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project
val fabricVersion: String by project
val fabricKotlinVersion: String by project

group = "cn.rtast"
version = pluginVersion

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
}
