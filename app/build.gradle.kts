plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.shoelistapp"

    // 1. Ubah bagian compileSdk menjadi seperti ini
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.shoelistapp"
        // 2. minSdk diubah ke 26 agar mendukung adaptive-icon di mipmap-anydpi
        minSdk = 26
        // 3. targetSdk disamakan dengan compileSdk
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // 1. Lifecycle & ViewModel Compose (Ini yang memperbaiki error 'viewModel' & 'compose')
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // 2. Coil Compose (Untuk load gambar dari internet)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // 3. Coroutines (Untuk proses asynchronous / background thread)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // 4. Retrofit & Gson (Untuk tembak REST API)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // untuk pindah halaman (Navigasi)
    implementation("androidx.navigation:navigation-compose:2.7.7")
}