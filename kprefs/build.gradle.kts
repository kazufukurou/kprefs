plugins {
  id("com.android.library")
  id("maven-publish")
  kotlin("android")
}

android {
  compileSdkVersion(30)

  buildFeatures {
    buildConfig = false
  }

  defaultConfig {
    minSdkVersion(14)
  }
}

dependencies {
  implementation(libs.kotlinStdlib)

  testImplementation(libs.robolectric)
  testImplementation(libs.kotlinTestJunit)
  testImplementation(libs.androidxTestExtJunit)
}

afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("release") {
        from(components.findByName("release"))
      }
    }
  }
}
