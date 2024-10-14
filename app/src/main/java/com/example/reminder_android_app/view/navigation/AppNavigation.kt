package com.example.reminder_android_app.view.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reminder_android_app.view.pages.ReminderPage
import com.example.reminder_android_app.view.pages.SavedRemindersPage
import com.example.reminder_android_app.view.pages.SettingsPage

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "reminder",
        modifier = modifier.fillMaxSize()
    ) {
        composable("reminder") { ReminderPage() }
        composable("saved") { SavedRemindersPage() }
        composable("settings") { SettingsPage() }
    }
}