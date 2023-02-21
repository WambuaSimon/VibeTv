// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-alpha04" apply false
    id("com.android.library") version "8.1.0-alpha04" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" apply false
    id("com.diffplug.spotless") version "5.15.0" apply false
}