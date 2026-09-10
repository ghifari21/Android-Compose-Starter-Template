package com.project.feat.home.domain.usecase

import com.project.common.exception.AppException
import com.project.feat.home.domain.model.HomeItem
import com.project.feat.home.domain.repository.HomeRepository
import javax.inject.Inject

class SearchItemsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(query: String): List<HomeItem> {
        val result = repository.searchItems(query)
        if (result.isEmpty()) {
            // Throw an error if no item matches the query
            throw AppException.UnknownException(message = "No item found for keyword '$query'.")
        }
        return result
    }
}
