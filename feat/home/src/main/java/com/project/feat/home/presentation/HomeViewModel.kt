package com.project.feat.home.presentation

import com.project.common.base.BaseViewModel
import com.project.feat.home.domain.usecase.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>(HomeState()) {

    init {
        // Load initial data upon creation
        setEvent(HomeEvent.LoadInitialData)
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadInitialData -> {
                fetchItems(query = "")
            }
            is HomeEvent.OnSearchQueryChanged -> {
                updateState { copy(searchQuery = event.query) }
            }
            is HomeEvent.OnSearchClicked -> {
                // Use current search query from state
                fetchItems(query = uiState.value.data.searchQuery)
            }
            is HomeEvent.OnItemClicked -> {
                setEffect { HomeEffect.NavigateToDetail(event.id, event.title) }
            }
        }
    }

    private fun fetchItems(query: String) {
        // Use safeLaunch with a key to prevent request stacking (Single-flight)
        safeLaunch(key = "fetch_home_items") {
            val result = searchItemsUseCase(query)
            updateState { copy(items = result) }
        }
    }
}
