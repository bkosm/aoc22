plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
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

    register("new") {
        doFirst {
            val number = project.properties["day"] as? String ?: error("Missing day prop")
            val srcPrefix = "$rootDir/src"

            listOf(
                "$srcPrefix/data/Day$number.txt",
                "$srcPrefix/data/Day${number}_test.txt",
            ).map(::File).forEach {
                if (it.exists().not()) {
                    it.createNewFile()
                } else {
                    error("File already exists: $it")
                }
            }

            (File("$srcPrefix/Template.kt") to File("$srcPrefix/Day$number.kt")).run {
                if (second.exists().not()) {
                    val content = first.readLines().map { it.replace("Template", "Day$number") }
                    second.writeText(content.joinToString("\n"))
                } else {
                    error("Code file already exists: $second")
                }
            }
        }
    }
}

dependencies {
    implementation("com.github.bkosm:ktuple:1.3.0")
    implementation(kotlin("reflect"))
    implementation(platform("dev.forkhandles:forkhandles-bom:2.3.0.0"))
    implementation("dev.forkhandles:result4k")
}
