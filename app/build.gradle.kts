import com.unlck.kotlin.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ProjectConfiguration.compileSdk
    buildToolsVersion = ProjectConfiguration.buildToolsVersion
    defaultConfig {
        applicationId = ProjectConfiguration.applicationId
        minSdk = ProjectConfiguration.minSdk
        targetSdk = ProjectConfiguration.targetSdk
        multiDexEnabled = true
        versionCode = ProjectConfiguration.versionCode
        versionName = ProjectConfiguration.versionName
        testInstrumentationRunner = ProjectConfiguration.hiltTestInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Libraries.kotlinVersion}")
    implementation("androidx.appcompat:appcompat:${Libraries.appCompatVersion}")
    implementation("androidx.appcompat:appcompat-resources:${Libraries.appCompatVersion}")
    implementation("androidx.core:core-ktx:${Libraries.appCompatCoreVersion}")
    implementation("androidx.constraintlayout:constraintlayout:${Libraries.constraintlayoutVersion}")
    implementation("com.google.android.material:material:${Libraries.materialDesignVersion}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))

    // for tests
    testImplementation("junit:junit:${TestingDependencies.junitVersion}")
    androidTestImplementation("androidx.test:runner:${TestingDependencies.testRunnerVerison}")
    androidTestImplementation("androidx.test.ext:junit:${TestingDependencies.junitTestVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${TestingDependencies.espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${TestingDependencies.espressoVersion}")
    androidTestImplementation("androidx.test.espresso.idling:idling-concurrent:${TestingDependencies.espressoVersion}")
    implementation("androidx.test.espresso:espresso-idling-resource:${TestingDependencies.espressoVersion}")
    debugImplementation("androidx.fragment:fragment-testing:${TestingDependencies.fragmentTestingVersion}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${DaggerHiltDependencies.daggerHiltVersion}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${DaggerHiltDependencies.daggerHiltVersion}")
    androidTestImplementation("org.mockito:mockito-core:${TestingDependencies.mockitoVersion}")
    testImplementation("org.mockito:mockito-core:${TestingDependencies.mockitoVersion}")
    androidTestImplementation("org.mockito:mockito-android:4.1.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libraries.coroutinesVersion}")

    testImplementation("androidx.test:core:${TestingDependencies.coreTestVersion}")

    // Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:${Libraries.navigationVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${Libraries.navigationVersion}")
    androidTestImplementation("androidx.navigation:navigation-testing:${Libraries.navigationVersion}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Libraries.coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Libraries.coroutinesVersion}")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${LifecycleDependencies.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${LifecycleDependencies.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${LifecycleDependencies.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${LifecycleDependencies.lifecycleVersion}")

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

    // Picasso
    implementation("com.squareup.picasso:picasso:${Libraries.PicassoVersion}")
    implementation("androidx.palette:palette-ktx:1.0.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}