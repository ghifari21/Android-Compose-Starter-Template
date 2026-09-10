# Android Compose Starter Template 🚀

A modern, production-ready, highly modularized Android Jetpack Compose starter template. This template uses the latest official architecture recommendations from Google, making it scalable for both indie and enterprise-level apps.

## 🏗️ Architecture & Tech Stack

- **UI Framework:** Jetpack Compose (100% Declarative UI)
- **Architecture:** Clean Architecture + MVI (Model-View-Intent)
- **Navigation:** Type-Safe Navigation Compose (Navigation 2.8+)
- **Dependency Injection:** Dagger Hilt
- **Network Layer:** Retrofit + OkHttp + Kotlinx Serialization + Chucker (for intercepting API logs)
- **Local Storage:** Room Database & DataStore Preferences
- **Build System:** Gradle Kotlin DSL with **Convention Plugins** (`build-logic`) ala *Now-in-Android*.

---

## 🛠️ Getting Started (Setup)

When you clone this template for a new project, you can automatically configure the Package Name and App Name using the built-in Python script.

1. Open your terminal at the root directory of the project.
2. Run the `setup.py` script:
   ```bash
   # Usage: python setup.py <new.package.name> "Your App Name"
   python setup.py com.mycompany.awesomeapp "Awesome App"
   ```
3. Open Android Studio and click **Sync Project with Gradle Files**.
4. You can now safely delete `setup.py`.

---

## 📂 Project Structure

The project is highly modularized to ensure separation of concerns and faster build times:

- **`app`**: The main entry point. Contains `BaseApp`, `MainActivity`, and the `MainScreen` (Bottom Navigation skeleton).
- **`build-logic`**: Contains Gradle Convention Plugins. Instead of repeating dependencies, features just apply `alias(libs.plugins.convention.feature)`.
- **`common`**: Core MVI classes (`BaseViewModel`, `UiState`), App Exceptions, Utils, and the Design System (`Theme`, `Colors`, `Type`).
- **`core:data`**: The Data layer containing `NetworkModule`, `DatabaseModule`, `AuthInterceptor`, and `SessionManager`.
- **`core:domain`**: Abstractions and UseCases.
- **`core:navigation`**: Contains the `BaseNavHost` wrapper designed for Type-safe Navigation and smooth screen transitions.
- **`feat:*`**: Feature modules containing Domain, Data, and Presentation logic for specific screens (e.g., `feat:home`).

---

## ⚡ Creating a New Feature

To maintain architectural consistency, you can generate a new feature module automatically using the provided bash script.

```bash
bash create_feature.sh <feature_name>

# Example:
bash create_feature.sh profile
```

**What the script does automatically:**
1. Generates `feat/profile` with Domain, Data, and Presentation folders.
2. Creates `ProfileViewModel`, `ProfileScreen`, and `ProfileRoutes` (Type-safe).
3. Applies the feature convention plugin in `build.gradle.kts`.
4. Automatically wires the module into `settings.gradle.kts` and `app/build.gradle.kts`.

After running the script, just hit **Sync Gradle** in Android Studio!

---

## 🧠 MVI Implementation Guide

Every `ViewModel` in this project inherits from `BaseViewModel<Event, State, Effect>`. 

- **State:** The data rendered by the UI. Automatically wrapped in a loading/error state.
- **Event:** User actions triggered from the UI (e.g., `OnSearchClicked`).
- **Effect:** One-time actions like Navigation, Toasts, or Snackbars.

### Safe Asynchronous Calls
Use the built-in `safeLaunch` function in your ViewModels to handle API calls. It automatically controls the Loading and Error states, and supports **Single-flight requests** (canceling previous jobs if fired rapidly).

```kotlin
private fun fetchItems() {
    safeLaunch(key = "fetch_items") {
        val data = repository.getItems()
        updateState { copy(items = data) }
    }
}
```

---

## 🤝 Contributing
Feel free to open an issue or submit a PR if you want to improve this template!
