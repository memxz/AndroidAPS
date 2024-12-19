import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/google")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google() // Fallback to Google repository
        mavenCentral() // Fallback to Maven Central repository
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.1")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        classpath(kotlin("gradle-plugin", version = Libs.Kotlin.kotlin))
        classpath(kotlin("allopen", version = Libs.Kotlin.kotlin))
        classpath(kotlin("serialization", version = Libs.Kotlin.kotlin))
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
}

allprojects {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/google")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        maven("https://jitpack.io")
        // add google mavenrepo & maven central
        google()
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlin.ExperimentalUnsignedTypes",
                "-Xjvm-default=all"     //Support @JvmDefault
            )
            jvmTarget = "11"
        }
    }
    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            val compilerArgs = options.compilerArgs
            compilerArgs.add("-Xlint:deprecation")
            compilerArgs.add("-Xlint:unchecked")
        }
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "jacoco")
}

// Include QRGen dependency using JitPack
subprojects {
    dependencies {
        implementation("com.github.kenglxn:QRGen:3.0.1") // Resolving QRGen from JitPack
    }
}

// Setup all reports aggregation
apply(from = "jacoco_aggregation.gradle.kts")

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}

// Exclude native libraries if not needed
configurations.all {
    exclude(group = "com.github.kenglxn.QRGen", module = "android")
}
