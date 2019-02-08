import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("org.jetbrains.kotlin.plugin.spring").version("1.3.20")
    id("org.springframework.boot").version("2.1.2.RELEASE")
    id("io.spring.dependency-management").version("1.0.6.RELEASE")
    application
    java
}

val junitVersion = "5.3.2"
val spekVersion = "2.0.0-rc.1"

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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application {
    mainClassName = "wild.monitor.AppKt"
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}