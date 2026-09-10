package com.project.feat.home.domain.repository

import com.project.feat.home.domain.model.HomeItem

interface HomeRepository {
    suspend fun searchItems(query: String): List<HomeItem>
}
