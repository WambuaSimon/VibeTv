import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.diffplug.spotless")

}

subprojects {
    apply(plugin = "$project.rootDir/spotless.gradle")
}
val keystorePropertiesFile = rootProject.file("keystore.properties")

val keystoreProperties = Properties()

keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.vibetv"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.vibetv"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }

    }

    flavorDimensions += "appType"
    productFlavors {
        create("mock") {
            dimension = "appType"
            applicationIdSuffix = ".mock"
            versionNameSuffix = "-dev"
            manifestPlaceholders ["applicationLabel"] = "VibeTv Mock"
        }

        create("production") {
            dimension = "appType"
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            if (variantBuilder.buildType == "release" && variantBuilder.productFlavors.toMap()["appType"] != "production") {
                variantBuilder.enable = false
            }
        }
    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-rc01")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose:compose-bom:2023.04.01")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.0-alpha07")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose:compose-bom:2023.01.00")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha06")

    implementation("androidx.compose.material:material-icons-extended:1.3.1")

    //hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //ROOM
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:2.5.0")

    //to use coroutines
    implementation("androidx.room:room-ktx:2.5.0")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.5.0")

    //handling images display
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-gif:2.2.2")

    implementation("com.flaviofaria:kenburnsview:1.0.7")
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("androidx.compose.ui:ui-util")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("me.onebone:toolbar-compose:2.3.5")

    implementation("com.google.accompanist:accompanist-flowlayout:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.29.1-alpha")
    //paging
    implementation("androidx.paging:paging-runtime:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.compose.ui:ui-test")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("app.cash.turbine:turbine:0.12.3")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation(" io.mockk:mockk-jvm:1.13.4")
    testImplementation(" io.kotest:kotest-assertions-shared-jvm:5.5.5")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:rules:1.5.0")
    // testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:1.6.4")
    //Tests
    /*
        testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:1.6.4")
        testImplementation ("app.cash.turbine:turbine:0.12.3")
        // To use the androidx.test.core APIs
        androidTestImplementation("androidx.test:core:1.5.0")
        // Kotlin extensions for androidx.test.core
        androidTestImplementation("androidx.test:core-ktx:1.5.0")
    // To use the Truth Extension APIs
        androidTestImplementation("androidx.test.ext:truth:1.5.0")

        // To use the androidx.test.runner APIs
        androidTestImplementation("androidx.test:runner:1.5.2")

        // To use android test orchestrator
        androidTestUtil("androidx.test:orchestrator:1.4.2")

        // AndroidJUnitRunner and JUnit Rules
        androidTestImplementation("androidx.test:rules:1.5.0")

        kaptTest("com.google.dagger:hilt-android-compiler:2.44.2")*/
}
