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

### Phrases Added to Greeting
- *(none yet)*

### Phrases Added to First
- *(none yet)*

### Phrases Added to Second
- *(none yet)*

### Phrases Added to Ending
- *(none yet)*

---

## [1.1.0] - 2026-03-11

### Added
- Daily morning pep talk notification delivered at 8:00 AM each day
- `PepTalkNotificationWorker` — WorkManager `CoroutineWorker` that generates a fresh pep talk and posts it as a system notification
- `MorningNotificationScheduler` — schedules the daily recurring work, calculates the correct initial delay so the first notification fires at 8:00 AM
- `POST_NOTIFICATIONS` permission declared in manifest (required for Android 13+)
- Runtime notification permission request on first app launch (Android 13+ only)
- "Morning Pep Talk" notification channel with default importance
- **Share** action button directly on the notification — opens the system share sheet without opening the app
- **Favorite** action button directly on the notification — saves the pep talk to Favorites and dismisses the notification
- Tapping the notification body opens the app and displays the exact pep talk from the notification (not a new random one)

---

## [1.0.0] - 2026-03-11

> **What's new:** Initial release of Pep Talk Generator! The app launches with a full phrase database powering 100,000+ unique motivational pep talks, a favorites system, sharing support, and a clean Material 3 UI.

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

### Phrases Added to Greeting
- Initial set of greeting phrases included in bundled database

### Phrases Added to First
- Initial set of first phrases included in bundled database

### Phrases Added to Second
- Initial set of second phrases included in bundled database

### Phrases Added to Ending
- Initial set of ending phrases included in bundled database
