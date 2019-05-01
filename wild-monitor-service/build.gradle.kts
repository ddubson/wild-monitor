import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.30")
    id("org.jetbrains.kotlin.plugin.spring").version("1.3.30")
    id("org.jetbrains.kotlin.plugin.jpa").version("1.3.30")
    id("org.springframework.boot").version("2.1.2.RELEASE")
    id("io.spring.dependency-management").version("1.0.6.RELEASE")
    application
    java
}

val junitVersion = "5.3.2"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    implementation("com.google.guava:guava:27.0.1-jre")
    implementation("org.flywaydb:flyway-core:5.2.4")
    runtime("com.h2database:h2:1.4.199")

    testImplementation("io.mockk:mockk:1.9.3.kotlin12")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
        exclude(module = "hamcrest-core")
        exclude(module = "hamcrest-library")
    }
    testImplementation("org.hamcrest:hamcrest-library:1.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application {
    mainClassName = "wild.monitor.WildMonitorAppKt"
}

tasks.withType(KotlinCompile::class).all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}