package com.example.mviexample.domain

import com.example.mviexample.UserResult
import com.example.mviexample.data.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadUsersInteractorImpl @Inject constructor(
    private val repository: UserRepository
) : LoadUsersInteractor {
    override suspend fun getUsers(): Flow<UserResult> = flow {
        emit(UserResult.Loading)

        try {
            val users = repository.getUsers()

            emit(
                UserResult.Success(users)
            )
        } catch (e: Exception) {

            emit(
                UserResult.Error(
                    e.message ?: "Unknown error"
                )
            )
        }
    }
}