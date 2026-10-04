plugins {
    kotlin("jvm") version "2.1.0"
    application
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

application {
    // `./gradlew run` executes every example. In IntelliJ, use the green ▶
    // next to any individual main() to run just that example.
    mainClass.set(project.findProperty("mainClass")?.toString() ?: "big_o.complexity.AllExamplesKt")
}
