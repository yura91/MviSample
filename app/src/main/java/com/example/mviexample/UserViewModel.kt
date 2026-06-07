package com.example.mviexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    private val _effects = Channel<UserEffect>()
    val effects = _effects.receiveAsFlow()

    fun process(intent: UserIntent) {
        when (intent) {
            UserIntent.LoadUsers -> loadUsers()

            UserIntent.Refresh -> loadUsers()

            is UserIntent.UserClicked -> {
                navigateToUser(intent.userId)
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            runCatching {
                repository.getUsers()
            }.onSuccess { users ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        users = users
                    )
                }

            }.onFailure { throwable ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.message
                    )
                }

                _effects.send(
                    UserEffect.ShowError(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun reduce(
        current: UserState,
        result: UserResult
    ): UserState {

        return when (result) {

            UserResult.Loading ->
                current.copy(
                    isLoading = true,
                    error = null
                )

            is UserResult.Success ->
                current.copy(
                    isLoading = false,
                    users = result.users
                )

            is UserResult.Error ->
                current.copy(
                    isLoading = false,
                    error = result.message
                )
        }
    }

    private fun navigateToUser(userId: Long) {
        viewModelScope.launch {
            _effects.send(
                UserEffect.NavigateToDetails(userId)
            )
        }
    }
}