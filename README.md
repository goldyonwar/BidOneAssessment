# BidOne Android Assessment

A modern, native Android application built with **Jetpack Compose**, demonstrating Clean Architecture, reactive UI patterns, and Android development best practices.

## Architecture
The application follows **Clean Architecture** principles layered with the **MVVM** (Model-View-ViewModel) pattern to ensure separation of concerns, testability, and scalability.

- **Domain Layer:** Contains pure Kotlin models (`@Immutable`) and Repository interfaces. This layer represents the business logic and is independent of any Android framework.
- **Data Layer:** Handles data operations using Repository implementations, API services (Retrofit), and DTO mapping.
- **UI Layer:** Built with Jetpack Compose. ViewModels expose UI State via `StateFlow` for a reactive and lifecycle-aware UI.

## Key Highlights & Performance
Addressing Jetpack Compose performance best practices:

1.  **Immutable State:** Domain models are marked with `@Immutable` to guarantee stability, allowing the Compose compiler to skip unnecessary recompositions (Smart Recomposition).
2.  **Optimized Lists:** Utilized `key` parameter in `LazyColumn` to maintain item identity, ensuring efficient scrolling and preventing UI glitches during updates.
3.  **Lifecycle-Aware State:** Implemented `collectAsStateWithLifecycle()` to safely consume flows in the UI, preventing resource waste when the app is in the background.
4.  **Efficient Image Loading:** Integrated **Coil** with crossfade animations and proper context handling for smooth image rendering.

## Architectural Decisions & Trade-offs
To balance simplicity with "real-world" best practices for this assessment, the following decisions were made:

### 1. Navigation Strategy
I chose to pass user details via **URL-encoded navigation arguments** to the Detail Screen.
* **Why:** To keep the implementation simple and reduce network calls for this specific static dataset.
* **Production Note:** In a large-scale production app, I would pass only the `userId` and fetch fresh data in the `UserDetailViewModel` (Single Source of Truth) to ensure data freshness and avoid `TransactionTooLargeException`.

### 2. Dynamic Image Generation
Since the provided API does not return image URLs, I integrated the **UI Avatars API**.
* **Implementation:** User avatars are dynamically generated based on their names, demonstrating the ability to handle network images professionally even without direct API support.

## Tech Stack
* **Language:** Kotlin
* **UI:** Jetpack Compose (Material3)
* **Dependency Injection:** Hilt
* **Networking:** Retrofit & Moshi
* **Concurrency:** Coroutines & Flow
* **Image Loading:** Coil
* **Testing:** JUnit4, MockK, Coroutines Test, Turbine

## Testing
Unit tests are included to ensure business logic reliability.
* **ViewModel Tests:** Verifies UI State transitions (Loading -> Success/Error) using `Turbine` and `MockK`.
* **Repository Tests:** Ensures correct mapping from DTOs to Domain models and error handling.