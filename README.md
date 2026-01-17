# Kotale

Kotale provides two mods for hytale that:

1. (kotlin.jar) The shaded stdlib, reflect and coroutine libraries for other mods
2. (kotale.jar) Extensions and utilities for modding hytale in kotlin

## Included Libraries

*   Kotlin StdLib (2.3.0)
*   Kotlin Reflect (2.3.0)
*   Kotlin Serialization (1.9.0)
*   Kotlin Serialization Json (1.9.0)
*   Kotlin Serialization Cbor (1.9.0)
*   Kotlin Coroutines (1.10.2)

## Usage (kotlin.jar)

To use the shaded kotlin stdlib in your mod, add Kotale:Kotlin as a dependency in your `manifest.json`:

```json
{
  "Dependencies": {
    "Kotale:Kotlin": "*"
  }
}
```

and download the latest version of `kotlin.jar` from the **Releases** or the **Curseforge** page and place it in your
`mods` folder. 


### Additional Shading
To avoid conflicts when shading other dependencies, you should exclude all
kotlin libraries from your mod jar, for example with the following `shadowJar` configuration:

```kotlin
tasks {
    shadowJar {
        exclude("kotlin/**")
        exclude("org/intellij/**")
        exclude("org/jetbrains/**")
        exclude("META-INF/**")

        minimize()
    }
}
```

Warning: This may or may not work as you expect it since kotlin libraries partially rely on kotlin_module files to work,
which are stored in meta-inf folders. In this case, you may need to selectively exclude only kotlin runtime libraries.

**In the best case, avoid using shadowing when you aren't including any other dependencies
that need shading and relocate if they are only internal**

## Usage (kotale.jar)

To use the Kotale utilities and extensions in your mod, add Kotale:Kotale as a dependency in your `manifest.json`:

```json
{
  "Dependencies": {
    "Kotale:Kotale": "*"
  }
}
```

and download the latest versions of both `kotlin.jar` and `kotale.jar` from the **Releases** or the **Curseforge**
page and place it in your `mods` folder. You can then use the utilities and extensions provided by Kotale in your 
mod code.

To now depend on the api of kotale in your build system, add the following to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven {
        name = "Averix"
        url = uri("https://repo.averix.tech/repository/maven-public/")
    }
}
```

```kotlin
dependencies {
    compileOnly("dev.helight.kotale:kotale:<latest-version>")
}
```