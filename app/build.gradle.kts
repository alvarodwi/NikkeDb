plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  kotlin("kapt")
  kotlin("plugin.serialization") version "1.8.10"
  id("kotlin-parcelize")
  id("dagger.hilt.android.plugin")
}

android {
  namespace = "me.varoa.nikkedb"
  compileSdk = 33

  defaultConfig {
    applicationId = "me.varoa.nikkedb"
    minSdk = 23
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "me.varoa.nikkedb.CustomTestRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android.txt"),
        "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility(JavaVersion.VERSION_11)
    targetCompatibility(JavaVersion.VERSION_11)
  }

  kotlinOptions {
    jvmTarget = "11"
    freeCompilerArgs = freeCompilerArgs + listOf(
      "-opt-in=kotlin.ExperimentalStdlibApi",
      "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      "-opt-in=kotlin.RequiresOptIn"
    )
  }

  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.4"
  }
  packagingOptions {
    resources.excludes.add("META-INF/*")
  }
  testOptions{
    packagingOptions {
      jniLibs {
        useLegacyPackaging = true
      }
    }
  }
}

kotlin {
  jvmToolchain(11)
}

dependencies {
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")
  // bom
  val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  // coroutine
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

  // core
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("androidx.activity:activity-compose:1.7.0")
  implementation("androidx.navigation:navigation-compose:2.5.3")

  // lifecycle
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

  // compose
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3:1.1.0-beta01")
  implementation("androidx.compose.material3:material3-window-size-class:1.1.0-beta01")
  implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.0")
  implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

  // di
  implementation("com.google.dagger:hilt-android:2.45")
  kapt("com.google.dagger:hilt-android-compiler:2.45")
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

  // data
  implementation("androidx.room:room-runtime:2.5.1")
  kapt("androidx.room:room-compiler:2.5.1")
  implementation("androidx.room:room-ktx:2.5.1")
  implementation("androidx.datastore:datastore-preferences:1.0.0")

  // networking
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
  implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

  // other
  implementation("io.coil-kt:coil-compose:2.2.2")
  implementation("com.squareup.logcat:logcat:0.1")
  implementation("id.zelory:compressor:3.0.1")

  // unit testing
  testImplementation("junit:junit:4.13.2")
  testImplementation("androidx.arch.core:core-testing:2.2.0")
  testImplementation("io.mockk:mockk:1.13.4")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
  testImplementation("app.cash.turbine:turbine:0.12.1")

  // idling resource
  implementation("androidx.test.espresso:espresso-idling-resource:3.5.1")

  // integration testing
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  // navigation testing
  androidTestImplementation("androidx.navigation:navigation-testing:2.5.3")

  // hilt testing
  androidTestImplementation("com.google.dagger:hilt-android-testing:2.45")
  kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.45")

  //mock web server
  androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
  androidTestImplementation("com.squareup.okhttp3:okhttp-tls:4.9.3")

  // compose tooling
  debugImplementation("androidx.compose.ui:ui-tooling")
}