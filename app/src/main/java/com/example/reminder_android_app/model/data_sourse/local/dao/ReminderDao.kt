package com.example.reminder_android_app.model.data_sourse.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.reminder_android_app.model.entities.Reminder

@Dao
interface ReminderDao {
    @Insert
    fun insertReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getAllReminders(): List<Reminder>
}
