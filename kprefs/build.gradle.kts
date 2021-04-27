plugins {
  id("com.android.library")
  id("maven-publish")
  kotlin("android")
}

setupDependencyUpdates()

android {
  compileSdkVersion(Sdk.compile)

  buildFeatures {
    buildConfig = false
  }

  defaultConfig {
    minSdkVersion(14)
  }
}

dependencies {
  implementation(Libs.kotlinStdLib)

  testImplementation(Libs.robolectric)
  testImplementation(Libs.kotlinTestJunit)
  testImplementation(Libs.androidxTestExtJunit)
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
