package com.project.feat.home.presentation

import com.project.feat.home.domain.model.HomeItem

data class HomeState(
    val items: List<HomeItem> = emptyList(),
    val searchQuery: String = ""
)

sealed class HomeEvent {
    object LoadInitialData : HomeEvent()
    data class OnSearchQueryChanged(val query: String) : HomeEvent()
    object OnSearchClicked : HomeEvent()
    data class OnItemClicked(val id: String, val title: String) : HomeEvent()
}

sealed class HomeEffect {
    data class NavigateToDetail(val id: String, val title: String) : HomeEffect()
}
