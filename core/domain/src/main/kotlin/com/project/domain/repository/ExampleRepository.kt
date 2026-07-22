package com.project.domain.repository

import com.project.model.model.ExampleModel
import kotlinx.coroutines.flow.Flow

interface ExampleRepository {
    fun getExample(): Flow<Result<ExampleModel>>
}