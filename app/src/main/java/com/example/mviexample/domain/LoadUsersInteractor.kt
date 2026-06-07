package com.example.mviexample.domain

import com.example.mviexample.UserResult
import com.example.mviexample.data.User
import kotlinx.coroutines.flow.Flow

interface LoadUsersInteractor {
    suspend fun getUsers(): Flow<UserResult>
}