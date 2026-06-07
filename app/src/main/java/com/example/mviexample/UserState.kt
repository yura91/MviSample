package com.example.mviexample

import com.example.mviexample.data.User

data class UserState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)
