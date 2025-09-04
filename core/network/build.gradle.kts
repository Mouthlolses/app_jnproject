plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.10"
}

android {
    namespace = "com.example.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}

dependencies {

    // Retrofit
    implementation(libs.retrofit)

    // Conversor do Kotlin Serialization
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Biblioteca de Serialização do Kotlin
    implementation(libs.kotlinx.serialization.json)

    // OkHttp (para interceptors e logs)
    implementation(libs.okhttp)

    // Interceptor de Log (para depuração)
    implementation(libs.logging.interceptor)

    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.analytics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}