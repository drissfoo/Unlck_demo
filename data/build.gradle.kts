import com.unlck.kotlin.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = ProjectConfiguration.compileSdk

    defaultConfig {
        minSdk = ProjectConfiguration.minSdk
        targetSdk = ProjectConfiguration.targetSdk

        testInstrumentationRunner = ProjectConfiguration.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${Libraries.appCompatCoreVersion}")
    implementation("androidx.appcompat:appcompat:${Libraries.appCompatVersion}")
    implementation(project(mapOf("path" to ":domain")))
    testImplementation("junit:junit:${TestingDependencies.junitVersion}")
    androidTestImplementation("androidx.test.ext:junit:${TestingDependencies.junitTestVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Libraries.coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Libraries.coroutinesVersion}")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:${DaggerHiltDependencies.daggerHiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${DaggerHiltDependencies.daggerHiltVersion}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:${DaggerHiltDependencies.hiltAndroidCompiler}")

    // Room
    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.3.0")

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:${NetworkingDependencies.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${NetworkingDependencies.retrofitVersion}")
}