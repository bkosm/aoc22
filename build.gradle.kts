plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io")}
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}

dependencies {
    implementation("com.github.bkosm:ktuple:1.2.0")
    implementation("com.github.bkosm:kpipe:1.0.0")
}
