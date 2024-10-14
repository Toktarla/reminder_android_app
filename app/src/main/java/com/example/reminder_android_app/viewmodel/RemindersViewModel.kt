package com.example.reminder_android_app.viewmodel

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder_android_app.R
import com.example.reminder_android_app.model.data_sourse.local.dao.ReminderDao
import com.example.reminder_android_app.model.entities.Reminder
import com.example.reminder_android_app.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val reminderDao: ReminderDao,
    private val alarmManager: AlarmManager
) : ViewModel() {

    var text by mutableStateOf("")
    var date by mutableStateOf("")
    var time by mutableStateOf("")

    var reminders = mutableStateListOf<Reminder>()
        private set

    fun addReminder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (date.isEmpty() && time.isEmpty()) {
                Toast.makeText(context, R.string.toast_datetime_error, Toast.LENGTH_LONG).show()
                return@launch
            } else if (text.isEmpty()) {
                Toast.makeText(context, R.string.toast_text_error, Toast.LENGTH_LONG).show()
                return@launch
            }

            if (date.isEmpty()) date = Utils.getCurrentDate()
            if (time.isEmpty()) time = "12:00"

            val reminder = Reminder(text = text, date = date, time = time)
            reminders.add(reminder)

            reminderDao.insertReminder(reminder) // Insert to Room Database

            text = ""
            date = ""
            time = ""

            scheduleNotification(context, reminder.date, reminder.time, reminder.text, reminder.id)
            sortReminders()
            Toast.makeText(context, R.string.toast_reminder_created, Toast.LENGTH_LONG).show()
        }
    }

    fun removeReminder(reminder: Reminder, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            reminders.remove(reminder)
            reminderDao.deleteReminder(reminder) // Remove from Room Database
            alarmManager.cancel(Utils.getPendingIntent(context, reminder.id, reminder.text))
            Toast.makeText(context, R.string.toast_reminder_removed, Toast.LENGTH_LONG).show()
        }
    }

    fun getReminders(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            reminders.clear()
            val reminderList = reminderDao.getAllReminders() // Fetch from Room Database
            reminderList.forEach { reminder ->
                if (Utils.isReminderInPast(reminder.date, reminder.time)) {
                    removeReminder(reminder, context)
                } else {
                    reminders.add(reminder)
                }
            }
            sortReminders()
        }
    }

    fun sortReminders() {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        reminders.sortWith(compareBy { reminder ->
            LocalDateTime.parse("${reminder.date} ${reminder.time}", formatter)
        })
    }

    fun scheduleNotification(context: Context, date: String, time: String, text: String, id: Int) {
        val dateTime = "$date $time"
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val triggerRime = sdf.parse(dateTime)?.time ?: return
        val pendingIntent = Utils.getPendingIntent(context, id, text)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerRime, pendingIntent)
        }

    }
}
