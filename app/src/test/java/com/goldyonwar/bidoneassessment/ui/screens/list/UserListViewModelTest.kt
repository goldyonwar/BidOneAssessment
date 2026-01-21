package com.goldyonwar.bidoneassessment.ui.screens.list

import app.cash.turbine.test
import com.goldyonwar.bidoneassessment.MainDispatcherRule
import com.goldyonwar.bidoneassessment.domain.model.User
import com.goldyonwar.bidoneassessment.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class UserListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: UserRepository = mockk()

    private lateinit var viewModel: UserListViewModel

    private val fakeUsers = listOf(
        User(1, "Gold", "gold@test.com", "021123456", "test.com", "BidOne")
    )

    @Test
    fun `when init, it should load users successfully`() = runTest {
        coEvery { repository.getUsers() } coAnswers {
            delay(100)
            Result.success(fakeUsers)
        }
        viewModel = UserListViewModel(repository)

        viewModel.uiState.test {
            assertEquals(UserListUiState.Loading, awaitItem())

            val successState = awaitItem() as UserListUiState.Success
            assertEquals(fakeUsers, successState.users)
        }
    }

    @Test
    fun `when repository fails, it should show error`() = runTest {
        val errorMessage = "Network Error"
        coEvery { repository.getUsers() } coAnswers {
            delay(100)
            Result.failure(Exception(errorMessage))
        }

        viewModel = UserListViewModel(repository)

        viewModel.uiState.test {
            assertEquals(UserListUiState.Loading, awaitItem())

            val errorState = awaitItem() as UserListUiState.Error
            assertEquals(errorMessage, errorState.message)
        }
    }
}