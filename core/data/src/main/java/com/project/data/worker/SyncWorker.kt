package com.project.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import timber.log.Timber

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.d("SyncWorker: Starting background synchronization...")
        
        return try {
            // Simulate network or database synchronization
            delay(2000)
            
            Timber.d("SyncWorker: Synchronization completed successfully.")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "SyncWorker: Synchronization failed")
            Result.retry()
        }
    }
}
