package com.project.data.repository

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.project.data.worker.SyncWorker
import com.project.domain.repository.SyncRepository
import javax.inject.Inject

class WorkManagerSyncRepository @Inject constructor(
    private val workManager: WorkManager
) : SyncRepository {
    override fun syncNow() {
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        workManager.enqueue(syncRequest)
    }
}
