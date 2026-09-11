package com.project.feat.home.presentation

import app.cash.turbine.test
import com.project.common.notification.AppNotificationManager
import com.project.domain.repository.SessionManager
import com.project.feat.home.domain.model.HomeItem
import com.project.feat.home.domain.usecase.SearchItemsUseCase
import com.project.testing.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchItemsUseCase = mockk<SearchItemsUseCase>()
    private val notificationManager = mockk<AppNotificationManager>(relaxed = true)
    private val sessionManager = mockk<SessionManager>(relaxed = true)

    private lateinit val viewModel: HomeViewModel

    private fun setupViewModel() {
        viewModel = HomeViewModel(
            searchItemsUseCase = searchItemsUseCase,
            notificationManager = notificationManager,
            sessionManager = sessionManager
        )
    }

    @Test
    fun `when LoadInitialData event is sent, fetch items`() = runTest {
        // Arrange
        val expectedItems = listOf(
            HomeItem("1", "Title 1", "Desc 1"),
            HomeItem("2", "Title 2", "Desc 2")
        )
        coEvery { searchItemsUseCase("") } returns expectedItems

        // Act
        setupViewModel() // Loads initial data in init block

        // Assert
        viewModel.uiState.test {
            val initialState = awaitItem()
            // In a real MVI with safeLaunch, the state updates asynchronously.
            // Turbine will catch the state emission.
            val loadedState = awaitItem()
            assertEquals(expectedItems, loadedState.data.items)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when OnTriggerLogout event is sent, clear session`() = runTest {
        // Arrange
        coEvery { searchItemsUseCase(any()) } returns emptyList()
        setupViewModel()

        // Act
        viewModel.setEvent(HomeEvent.OnTriggerLogout)

        // Assert
        // Allow coroutine to execute
        kotlinx.coroutines.test.runCurrent()
        coVerify(exactly = 1) { sessionManager.clearSession() }
    }
}
