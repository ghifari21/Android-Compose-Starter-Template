package com.project.common.di

import android.content.Context
import com.project.common.notification.AppNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideAppNotificationManager(
        @ApplicationContext context: Context
    ): AppNotificationManager {
        return AppNotificationManager(context)
    }
}
