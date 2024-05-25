val gsonVersion: String by project
val kotlinVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.gson)
}
