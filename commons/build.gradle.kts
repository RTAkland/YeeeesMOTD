plugins {
    kotlin("jvm") version("1.9.24")
}

val gsonVersion: String by project
val kotlinVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:${gsonVersion}")
}
