package com.example.mviexample

sealed interface UserEffect {

    data class NavigateToDetails(
        val userId: Long
    ) : UserEffect

    data class ShowError(
        val message: String
    ) : UserEffect
}