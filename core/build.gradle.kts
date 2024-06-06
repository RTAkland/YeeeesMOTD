dependencies {
    implementation(libs.gson)
    testImplementation(libs.kotlinTest)
}

tasks.test {
    useJUnitPlatform()
}
