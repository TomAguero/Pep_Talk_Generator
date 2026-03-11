# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Install debug APK to connected device/emulator
./gradlew installDebug

# Run unit tests
./gradlew test

# Run instrumented (on-device) tests
./gradlew connectedAndroidTest

# Clean build artifacts
./gradlew clean
```

## Architecture Overview

This is an Android app (Kotlin + Jetpack Compose) that generates random motivational "Pep Talks" with 104,000+ combinations. It follows **MVVM with a Repository pattern**.

### Layers

**Data Layer** (`app/src/main/java/com/example/peptalkgenerator/data/`):
- Room database backed by a pre-populated SQLite file at `app/src/main/assets/database/pepTalks.db`
- Two entities: `Phrase` (source phrases by type) and `PepTalk` (saved/favorited talks)
- `PepTalkRepository` is the single data access point — it orchestrates talk generation by selecting 4 random phrases (greeting + first + second + ending) from `PhraseDao` and saving them via `PepTalkDao`
- Database is initialized in `PepTalkApplication` using `createFromAsset`

**ViewModel Layer** (`model/`):
- One ViewModel per screen, injected via `ViewModelProvider.Factory` defined in `PepTalkApplication`
- State exposed as `StateFlow` / `Flow` collected in Compose

**UI Layer** (`ui/`):
- Jetpack Compose with Material3
- `PepTalkApp.kt` sets up the scaffold, drawer, and nav host
- `NavGraph.kt` defines all routes and passes ViewModels to screens
- Navigation uses a `DrawerState`-based navigation drawer (Home, New Pep Talk, Favorites)

### Key Data Flow

1. User taps "New Pep Talk" → `PepTalkScreenViewModel` calls `repository.generatePepTalk()`
2. Repository queries 4 random phrases by type from `PhraseDao`, combines them, and inserts the result via `PepTalkDao`
3. ViewModel exposes the current talk as `StateFlow<PepTalk?>`, UI recomposes

### Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material3
- **Database:** Room 2.5.2 (KSP annotation processing)
- **Async:** Coroutines + `Flow`
- **Navigation:** Navigation Compose
- **Min SDK:** 24 | **Target/Compile SDK:** 33 | **Java:** 17
