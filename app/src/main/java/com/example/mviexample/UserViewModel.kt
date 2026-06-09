package com.example.mviexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.domain.LoadUsersInteractor
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
    private val userInteractor: LoadUsersInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    private val _effects = Channel<UserEffect>()
    val effects = _effects.receiveAsFlow()

    fun process(intent: UserIntent) {
        when (intent) {
            UserIntent.LoadUsers -> loadUsers()

            UserIntent.Refresh -> loadUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {

            userInteractor.getUsers()
                .collect { result ->

                    _state.update { currentState ->
                        reduce(
                            currentState,
                            result
                        )
                    }
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
}