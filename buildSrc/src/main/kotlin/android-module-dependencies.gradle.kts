plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.compileSdk
    defaultConfig {
        minSdk = 28
        @Suppress("DEPRECATION")
        targetSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
        }
    }

    sourceSets {
        named("main") {
            jniLibs.srcDirs(listOf("src/main/jniLibs"))
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    lint {
        checkReleaseBuilds = false
        disable += "MissingTranslation"
        disable += "ExtraTranslation"
    }

    flavorDimensions.add("standard")
    productFlavors {
        create("full") {
            dimension = "standard"
        }
        create("pumpcontrol") {
            dimension = "standard"
        }
        create("aapsclient") {
            dimension = "standard"
        }
        create("aapsclient2") {
            dimension = "standard"
        }
    }

    // disable for modules here
    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    kapt(Libs.Dagger.androidProcessor)
    kapt(Libs.Dagger.compiler)
}
