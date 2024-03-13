plugins {
    kotlin("jvm") version "1.9.22"
}

group = "kr.mj.plugin"
version = "1.0.0"

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.hqservice.kr/repository/maven-public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("kr.hqservice", "hqframework-bukkit-nms", "1.0.1-SNAPSHOT")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}