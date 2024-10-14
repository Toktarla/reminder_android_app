package com.example.reminder_android_app.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import com.example.reminder_android_app.model.data_sourse.local.ReminderDatabase
import com.example.reminder_android_app.model.data_sourse.local.dao.ReminderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReminderModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReminderDatabase {
        return Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "reminder_database"
        ).build()
    }

    @Provides
    fun provideReminderDao(db: ReminderDatabase): ReminderDao {
        return db.reminderDao()
    }

    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

}
