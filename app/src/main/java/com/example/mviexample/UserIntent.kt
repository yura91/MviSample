package com.example.mviexample

sealed interface UserIntent {

    data object LoadUsers : UserIntent

    data object Refresh : UserIntent

    data class UserClicked(
        val userId: Long
    ) : UserIntent
}
