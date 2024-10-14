package com.example.reminder_android_app.view.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminder_android_app.R
import com.example.reminder_android_app.model.entities.Reminder
import com.example.reminder_android_app.viewmodel.RemindersViewModel

@Composable
fun ReminderItem(reminder: Reminder, viewModel: RemindersViewModel) {
    val context = LocalContext.current
    val deleteIcon: Painter = painterResource(id = androidx.core.R.drawable.ic_call_decline) // Replace with your icon

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.dark), RoundedCornerShape(12.dp))
            .border(1.dp, colorResource(id = R.color.blue), RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable {
                Toast.makeText(context, reminder.text, Toast.LENGTH_LONG).show()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = reminder.text,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${reminder.date} • ${reminder.time}",
                style = TextStyle(
                    color = colorResource(id = R.color.blue),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }

        Image(
            painter = deleteIcon,
            contentDescription = "Delete Reminder",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    viewModel.removeReminder(reminder, context)
                }
        )
    }
}
