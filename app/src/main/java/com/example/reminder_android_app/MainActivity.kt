package com.example.reminder_android_app

import android.app.AlarmManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.reminder_android_app.view.navigation.AppNavigation
import com.example.reminder_android_app.view.navigation.MyBottomNavBar
import com.example.reminder_android_app.ui.theme.ReminderAppTheme
import com.example.reminder_android_app.ui.theme.ThemeProvider
import com.example.reminder_android_app.viewmodel.ThemeViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var alarmManager: AlarmManager

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean -> if(!isGranted) {
        Toast.makeText(this, R.string.permission_warning, Toast.LENGTH_LONG).show()
    }
    }

    private val viewModel: ThemeViewModel by viewModels() // Ensure ViewModel is provided correctly

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = Color.BLACK

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            && !NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            && !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }

        setContent {
            val systemUiController: SystemUiController = rememberSystemUiController()

            systemUiController.isSystemBarsVisible = false
            systemUiController.isNavigationBarVisible = false

            val navController = rememberNavController()

            ThemeProvider(isDarkTheme = viewModel.isDarkTheme) {
                Scaffold(
                    bottomBar = { MyBottomNavBar(navController) }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
