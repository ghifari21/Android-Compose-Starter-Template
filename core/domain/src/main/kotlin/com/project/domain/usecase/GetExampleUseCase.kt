package com.project.domain.usecase

import com.project.domain.repository.ExampleRepository
import javax.inject.Inject

class GetExampleUseCase @Inject constructor(
    private val repository: ExampleRepository
) {
    operator fun invoke() = repository.getExample()
}