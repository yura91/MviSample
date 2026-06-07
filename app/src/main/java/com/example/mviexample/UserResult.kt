package com.example.mviexample

import com.example.mviexample.data.User

sealed interface UserResult {

    data object Loading : UserResult

    data class Success(
        val users: List<User>
    ) : UserResult

    data class Error(
        val message: String
    ) : UserResult
}