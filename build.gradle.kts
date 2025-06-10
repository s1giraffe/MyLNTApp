// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    //id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    // build.gradle.kts (Project level)

    //id("org.jetbrains.kotlin.kapt") version "1.9.10" apply false // Ensure this matches your Kotlin version
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.21" apply false // If you have pure Kotlin/Java modules
    id("org.jetbrains.kotlin.kapt") version "1.9.21" apply false //
}