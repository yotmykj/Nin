plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.ninplus.tv"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ninplus.tv"
        minSdk = 29
        targetSdk = 35

        versionCode = 2
        versionName = "0.2.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2025.01.00")
    implementation(composeBom)

    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.tv:tv-material:1.0.0")

    // NinEngine: NewPipeExtractor-KMP Android artifact (AAR на Maven Central)
    implementation("io.github.yushosei:newpipe-extractor-kmp-android:1.3.0")

    // Desugaring (требуется для NewPipe Extractor при minSdk < 33)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs_nio:2.1.4")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
