package com.project.starter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application entry point required for Hilt dependency injection.
 * Also used to initialize application-wide tools like Timber logging.
 */
@HiltAndroidApp
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber logging
        Timber.plant(Timber.DebugTree())
    }
}
