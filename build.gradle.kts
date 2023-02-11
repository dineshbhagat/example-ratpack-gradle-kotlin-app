import ratpack.gradle.*

val junitVersion by extra("5.9.0")
val spekVersion by extra("2.0.19")
val log4j by extra("2.19.0")

plugins {
    application
    kotlin("jvm") version "1.8.0"
    id("idea")
    id("java-library")
    id("java")
    //generates fatJar https://imperceptiblethoughts.com/shadow/
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.ratpack.ratpack-java") version "2.0.0-rc-1"
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    project.extensions.getByType(RatpackExtension::class.java).let { ratpack ->
        implementation(ratpack.dependency("guice"))
    }
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:$log4j")
    runtimeOnly("org.apache.logging.log4j:log4j-api:$log4j")
    runtimeOnly("org.apache.logging.log4j:log4j-core:$log4j")
    runtimeOnly("com.lmax:disruptor:3.3.4")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:${junitVersion}")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
}

application {
    mainClass.set("ratpack.example.kotlin.Main")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
