plugins {
    id("java")
    kotlin("jvm") version "1.7.22" // 코틀린 버전
    kotlin("plugin.jpa") version "1.7.22"
}

group = "team.belloo"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.springframework.data:spring-data-jpa:3.1.0")
    implementation("org.springframework:spring-context:6.0.9")

    implementation(project(":core"))
}

tasks.test {
    useJUnitPlatform()
}
