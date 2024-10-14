# Reminder Application

This is a Reminder Android Application built using modern Android development tools and libraries. The app allows users to write tasks, schedule reminders, and save them locally using Room. It also features a bottom navigation bar, Jetpack Compose for UI, Hilt for dependency injection, and ViewModel for managing UI-related data. Additionally, the app supports localization (English and Russian) and notifies users of upcoming tasks through notifications.

## Features

Task Creation: Users can create and schedule reminders.
Room Database: Reminders are saved locally using Room for persistent storage.
Bottom Navigation Bar: The app uses Jetpack Compose to create a clean and responsive bottom navigation bar.
App Navigation: Implemented with Jetpack Compose's Navigation component.
ViewModel: Used to handle UI-related data and separate logic from the UI layer.
Hilt Dependency Injection: Hilt is used to manage dependencies throughout the app.
Localization: The app supports both English and Russian languages.
Notifications: Sends notifications for scheduled reminders.

## Tech Stack

Kotlin: Programming language used.
Room: Local database for saving tasks.
DAO: Data Access Object for interacting with the Room database.
Jetpack Compose: Modern UI toolkit for building native UIs.
Hilt: Dependency injection for managing the lifecycle of objects.
ViewModel: Manages UI-related data in a lifecycle-conscious way.
LiveData/State: Used for reactive UI updates.
Notifications: Android Notification Manager to send reminders.
Localization: Support for multiple languages (English, Russian).

## Architecture
The app follows the MVVM (Model-View-ViewModel) architecture to maintain separation of concerns. Key components include:

Model: Manages the data layer, including the Room database and DAO.
ViewModel: Handles the business logic and prepares data for the UI.
View: Composed of Jetpack Compose functions to display the UI.

## Notifications
The app sends notifications using the NotificationManager to alert users of their scheduled tasks. Ensure that the app has the required permissions to send notifications.