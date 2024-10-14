package com.example.reminder_android_app.view.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reminder_android_app.R
import com.example.reminder_android_app.viewmodel.RemindersViewModel
import com.example.reminder_android_app.view.components.ReminderItem

@Composable
fun SavedRemindersPage(viewModel: RemindersViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getReminders(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.saved_reminders),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between items
        ) {
            itemsIndexed(viewModel.reminders) { _, reminder ->
                ReminderItem(reminder, viewModel)
            }
        }
    }
}
