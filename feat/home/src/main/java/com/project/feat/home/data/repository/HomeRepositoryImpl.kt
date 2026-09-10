package com.project.feat.home.data.repository

import com.project.feat.home.domain.model.HomeItem
import com.project.feat.home.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    private val mockData = listOf(
        HomeItem("1", "Clean Architecture", "Belajar tentang pembagian layer dan separation of concerns."),
        HomeItem("2", "MVI Pattern", "Model-View-Intent untuk state management yang predictable."),
        HomeItem("3", "Jetpack Compose", "Membangun UI deklaratif di Android dengan mudah."),
        HomeItem("4", "Kotlin Coroutines", "Manajemen asynchronous programming yang sangat efisien."),
        HomeItem("5", "Hilt Dependency Injection", "Mempermudah penyediaan dependency pada aplikasi.")
    )

    override suspend fun searchItems(query: String): List<HomeItem> {
        // Simulate network delay
        delay(1000)
        
        return if (query.trim().isEmpty()) {
            mockData
        } else {
            mockData.filter { it.title.contains(query, ignoreCase = true) }
        }
    }
}
