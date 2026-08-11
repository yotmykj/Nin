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

    // NinEngine: современный WebView API
    implementation("androidx.webkit:webkit:1.12.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
