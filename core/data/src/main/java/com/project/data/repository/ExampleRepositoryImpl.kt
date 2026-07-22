package com.project.data.repository

import com.project.common.base.BaseRepository
import com.project.data.source.local.datastore.ExampleDataStore
import com.project.data.source.local.db.dao.ExampleDao
import com.project.data.source.remote.ExampleApiService
import com.project.domain.repository.ExampleRepository
import com.project.model.model.ExampleModel
import com.project.model.toModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val apiService: ExampleApiService,
    private val exampleDao: ExampleDao,
    private val dataStore: ExampleDataStore
) : BaseRepository(), ExampleRepository {

    override fun getExample(): Flow<Result<ExampleModel>> {
        // Example logic: returning data from remote and mapping it to model
        // In a real scenario, you might want to cache this in Room or DataStore
        return safeCall(
            block = { apiService.getExample() },
            transform = { it.toModel() }
        )
    }
}
