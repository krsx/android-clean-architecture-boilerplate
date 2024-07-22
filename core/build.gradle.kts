import com.android.build.api.variant.BuildConfigField

plugins {
    alias(libs.plugins.library.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("com.android.library")
//    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.project.core"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        minSdk = 24
        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //flow
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //timber
    implementation(libs.timber)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}