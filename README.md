[![License](https://img.shields.io/badge/License-Apache%202.0-orange.svg)](https://opensource.org/licenses/Apache-2.0)

# kprefs
Kotlin delegates for SharedPreferences

## Setup
In root `build.gradle`
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In module `build.gradle`
```
dependencies {
    implementation 'com.artyommironov.kprefs:kprefs:1.0.0'
}
```

## Usage
```kotlin
class Prefs(prefs: SharedPreferences) {
    var text by prefs.string()
    var checked by prefs.boolean()
    var progress by prefs.int()
    var notes by prefs.stringSet()
    var kaomoji by prefs.enum(Kaomoji.Smile)

    enum class Kaomoji(val text: String) {
        Smile("( ͡ᵔ ͜ʖ ͡ᵔ)"),
        Sad("( ͡° ʖ̯ ͡°)"),
        Shock("(ʘ ͟ʖ ʘ)"),
    }
}
```

## License
```txt
Copyright 2021 Artyom Mironov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
