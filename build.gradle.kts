plugins {
    kotlin("jvm") version "2.3.10"
}

group = "com.gamezone"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.sun.mail:jakarta.mail:2.0.1")
}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}