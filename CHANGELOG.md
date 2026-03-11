# Changelog

All notable changes to **Pep Talk Generator** will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]
> Add work-in-progress items here as you go. When ready to release,
> rename this section to the new version + today's date.

### Added
- *(nothing yet)*

---

## [1.0.0] - 2026-03-11

### Added
- Main screen with randomly generated pep talks (100,000+ possible combinations)
- Pep talks composed of four parts: greeting, first, second, and ending phrases
- Favorites system: save pep talks for later from the main screen
- Favorites screen: browse all saved pep talks
- Pep Talk Details screen: view, share, or remove individual favorites
- Share pep talks via Android share sheet from both the main and details screens
- Navigation drawer providing access to all screens
- Bottom app bar with New Pep Talk, Favorite, and Share actions
- Room database with `phrases` and `PepTalks` tables
- Pre-populated phrase database loaded from bundled asset
- MVVM architecture with ViewModel + Repository pattern
- Kotlin Coroutines + Flow for async data
- Material 3 theming with Jetpack Compose UI
- Navigation Compose with back stack support
- Snackbar confirmation with "Go to Favorites" action after saving
- Confirmation dialog before removing a pep talk from favorites
