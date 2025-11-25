plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.caririfest.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.caririfest.app_jnproject"
        minSdk = 31
        targetSdk = 36
        versionCode = 8
        versionName = "1.1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    //permission
    implementation(libs.accompanist.permissions)

    //firebase
    implementation(platform(libs.firebase.bom.v3222))
    implementation(libs.firebase.crashlytics.ndk)
    implementation(libs.google.firebase.analytics)

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //MapBoxSDK
    implementation(libs.android.ndk27)
    implementation(libs.maps.compose.ndk27)
    implementation(libs.navigationcore.android.ndk27)
    implementation(libs.autofill.ndk27)
    implementation(libs.discover.ndk27)
    implementation(libs.place.autocomplete.ndk27)
    implementation(libs.offline.ndk27)
    implementation(libs.mapbox.search.android.ndk27)
    implementation(libs.mapbox.search.android.ui.ndk27)


    //módulo core-network
    implementation(project(":core:network"))
    //módulo core-data
    implementation(project(":core:data"))

    implementation(libs.android.sdk)

    //WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    //GCMNetworkManager support
    implementation(libs.androidx.work.gcm)
    implementation(libs.places)
    // optional - Test helpers
    androidTestImplementation(libs.androidx.work.testing)
    // optional - Multiprocess support
    implementation(libs.androidx.work.multiprocess)


    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava3)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.foundation)
    implementation(libs.material3)
    implementation(libs.androidx.animation)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}