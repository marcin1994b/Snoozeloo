import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

//    kotlin("plugin.serialization")

    id("kotlin-parcelize")
    id("kotlin-kapt")

//    // ROOM
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.room)

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    sourceSets {

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(compose.material3)

            // time
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

            // appyx
            api("com.bumble.appyx:appyx-navigation:2.0.0-alpha10")
            api("com.bumble.appyx:backstack:2.0.0-alpha10")
            api("com.bumble.appyx:spotlight:2.0.0-alpha10")

            //kodein
            api("org.kodein.di:kodein-di:7.22.0")

            // Room
//            implementation(libs.room.runtime)
//            implementation(libs.sqlite.bundled)

        }
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

//            implementation(libs.room.runtime.android)
        }

    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
}

android {
    namespace = "org.marcin1994b.snoozeloo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.marcin1994b.snoozeloo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

//room {
//    schemaDirectory("$projectDir/schemas")
//}

dependencies {
    debugImplementation(compose.uiTooling)

//    // ROOM
//    add("kspCommonMainMetadata", libs.room.compiler)
}

