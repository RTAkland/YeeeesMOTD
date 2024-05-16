val gsonVersion: String by project
val kotlinVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:${gsonVersion}")
}
