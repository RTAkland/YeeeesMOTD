import org.jetbrains.kotlin.gradle.dsl.JvmTarget

dependencies {
    implementation(libs.gson)
    testImplementation(libs.kotlinTest)
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_17
}

tasks.compileJava {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}