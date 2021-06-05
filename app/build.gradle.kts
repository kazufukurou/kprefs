plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  val sdkVersion = 30
  compileSdkVersion(sdkVersion)

  buildFeatures {
    viewBinding = true
  }

  defaultConfig {
    applicationId = "com.artyommironov.kprefssample"
    minSdkVersion(14)
    targetSdkVersion(sdkVersion)
    versionCode = 1
    versionName = "1.0"
  }
}

dependencies {
  implementation(project(":kprefs"))
  implementation(libs.kotlinStdlib)
}
