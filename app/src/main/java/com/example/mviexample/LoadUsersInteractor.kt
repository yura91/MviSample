package com.example.mviexample

import com.example.mviexample.data.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadUsersInteractor @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(): Flow<UserResult> = flow {

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