package com.example.reminder_android_app.view.pages

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Switch
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import java.util.Locale
import android.content.SharedPreferences
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminder_android_app.R
import com.example.reminder_android_app.viewmodel.ThemeViewModel

const val PREFS_NAME = "app_prefs"
const val LANGUAGE_KEY = "language"

@Composable
fun SettingsPage(viewModel: ThemeViewModel = viewModel()) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var selectedLanguage by remember { mutableStateOf(loadLanguagePreference(sharedPreferences)) }
    val isDarkTheme by remember { derivedStateOf { viewModel.isDarkTheme } }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.settings_page_title),
            fontSize = 24.sp,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        LocaleSelector(selectedLanguage) { newLocale ->
            selectedLanguage = newLocale
            saveLanguagePreference(sharedPreferences, newLocale)
            changeLocaleAndRestart(context, newLocale)
        }

        // Theme Toggle
        ThemeSwitcher(isDarkTheme) { isDark ->
            print(viewModel.isDarkTheme)
            viewModel.toggleTheme()
        }

        // Notifications Toggle
        NotificationsToggle(notificationsEnabled) { isEnabled ->
            notificationsEnabled = isEnabled
            // Handle notifications enabling/disabling here
        }
    }
}


fun loadLanguagePreference(sharedPreferences: SharedPreferences): String {
    return sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
}

fun saveLanguagePreference(sharedPreferences: SharedPreferences, language: String) {
    with(sharedPreferences.edit()) {
        putString(LANGUAGE_KEY, language)
        apply()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocaleSelector(selectedLanguage: String, onLanguageChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("en", "ru")
    val labels = mapOf("en" to stringResource(id = R.string.language_english), "ru" to stringResource(id = R.string.language_russian))

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = labels[selectedLanguage] ?: stringResource(id = R.string.select_language),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { language ->
                DropdownMenuItem(onClick = {
                    onLanguageChange(language)
                    expanded = false
                }) {
                    Text(text = labels[language] ?: stringResource(id = R.string.unknown_language))
                }
            }
        }
    }
}

@Composable
fun ThemeSwitcher(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.dark_theme), fontSize = 16.sp)
        Switch(
            checked = isDarkTheme,
            onCheckedChange = onThemeChange
        )
    }
}

@Composable
fun NotificationsToggle(isEnabled: Boolean, onToggleChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.enable_notifications), fontSize = 16.sp)
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggleChange
        )
    }
}

fun changeLocaleAndRestart(context: Context, localeCode: String) {
    val locale = when (localeCode) {
        "ru" -> Locale("ru")
        else -> Locale("en")
    }
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration).apply {
        setLocale(locale)
    }
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Restart the app to apply the new locale
    val intent = (context as? ComponentActivity)?.intent?.apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsPage() {
    SettingsPage()
}
