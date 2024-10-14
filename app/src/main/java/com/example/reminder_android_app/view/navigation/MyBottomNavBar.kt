package com.example.reminder_android_app.view.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.reminder_android_app.R

@Composable
fun MyBottomNavBar(navController: NavController) {
    BottomNavigation(
        elevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = stringResource(id = R.string.app_title)) },
            label = { Text(stringResource(id = R.string.app_title)) },
            selected = currentRoute == "reminder",
            onClick = { navController.navigate("reminder") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = stringResource(id = R.string.saved_page_title))},
            label = { Text(stringResource(id = R.string.saved_reminders)) },
            selected = currentRoute == "saved",
            onClick = { navController.navigate("saved") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = stringResource(id = R.string.settings_page_title))},
            label = { Text(stringResource(id = R.string.settings_page_title)) },
            selected = currentRoute == "settings",
            onClick = { navController.navigate("settings") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyBottomNavBar() {
    val navController = rememberNavController()
    MyBottomNavBar(navController)
}
