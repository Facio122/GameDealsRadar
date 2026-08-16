# Fix sync error: Argument type mismatch for KSP dependency

The project is failing to sync because the `ksp(...)` call in `shared/build.gradle.kts` is being incorrectly resolved as a call to the `ksp` extension (which expects an `Action<KspExtension>`) instead of a dependency configuration. This is a common naming conflict in Kotlin DSL when the KSP plugin is applied.

Additionally, the Room library references in `libs.versions.toml` appear to have incorrect group and module names (`androidx.room3:room3-*` instead of `androidx.room:room-*`), which will cause resolution errors after the sync issue is fixed.

## Proposed Changes

### [shared module](file:///C:/Users/piotr/AndroidStudioProjects/GameDealsRadar/shared/build.gradle.kts)

#### [MODIFY] [build.gradle.kts](file:///C:/Users/piotr/AndroidStudioProjects/GameDealsRadar/shared/build.gradle.kts)
- Replace `ksp(libs.androidx.room3.compiler)` with `add("ksp", libs.androidx.room3.compiler)` to disambiguate the call.
- Alternatively, move the KSP dependency to the top-level `dependencies` block where it is more idiomatic for KSP.

### [Gradle Configuration](file:///C:/Users/piotr/AndroidStudioProjects/GameDealsRadar/gradle/libs.versions.toml)

#### [MODIFY] [libs.versions.toml](file:///C:/Users/piotr/AndroidStudioProjects/GameDealsRadar/gradle/libs.versions.toml)
- Correct the Room artifact definitions:
    - `androidx.room3:room3-compiler` -> `androidx.room:room-compiler`
    - `androidx.room3:room3-runtime` -> `androidx.room:room-runtime`
- (Optional) Verify if version `3.0.1` is correct for the current environment (2026).

## Verification Plan

### Automated Tests
- Run `./gradlew :shared:assemble` to verify that the build script compiles and dependencies are resolved.
- Run a Gradle sync in Android Studio.

### Manual Verification
- Check that the `ksp` extension is still usable for configuration (e.g., `ksp { arg(...) }`) while the dependency is correctly added.
