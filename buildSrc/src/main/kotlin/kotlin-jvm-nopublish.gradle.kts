// The code in this file is a convention plugin - a Gradle mechanism for sharing reusable build logic.
// `buildSrc` is a Gradle-recognized directory and every plugin there will be easily available in the rest of the build.
package buildsrc.convention

import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin in JVM projects.
    kotlin("jvm")
}

val buildVersion
    get() = findProperty("build.version")?.toString()
        ?: System.getenv("BUILD_VERSION")
        ?: extraProperties["global.version"]!! as String

version = buildVersion
group = "dev.helight.kotale"

repositories {
    mavenCentral()
    maven {
        name = "Averix"
        url = uri("https://repo.averix.tech/repository/maven-public/")
    }
    maven {
        name = "Hytale Private"
        url = uri("https://repo.averix.tech/repository/hytale-private/")
        credentials {
            username = project.findProperty("averix.user")?.toString() ?: System.getenv("KOTALE_USER")
            password = project.findProperty("averix.key")?.toString() ?: System.getenv("KOTALE_KEY")
        }
    }
}


kotlin {
    // Use a specific Java version to make it easier to work in different environments.
    jvmToolchain(25)
}

tasks.withType<Test>().configureEach {
    // Configure all test Gradle tasks to use JUnitPlatform.
    useJUnitPlatform()

    // Log information about all test results, not only the failed ones.
    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }
}

tasks.withType<ProcessResources>().configureEach {
    filesMatching("manifest.json") {
        val version = System.getenv("BUILD_VERSION")?.takeIf { it.isNotBlank() } ?: "0.0.1"
        expand("buildVersion" to version)
    }
}