package com.goldyonwar.bidoneassessment.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldyonwar.bidoneassessment.domain.model.User
import com.goldyonwar.bidoneassessment.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI State definition
sealed interface UserListUiState {
    data object Loading : UserListUiState
    data class Success(val users: List<User>) : UserListUiState
    data class Error(val message: String) : UserListUiState
}

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserListUiState.Loading

            repository.getUsers()
                .onSuccess { users ->
                    _uiState.value = UserListUiState.Success(users)
                }
                .onFailure { error ->
                    _uiState.value = UserListUiState.Error(
                        error.message ?: "An unexpected error occurred."
                    )
                }
        }
    }
}