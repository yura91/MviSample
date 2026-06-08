package com.example.mviexample.data

import jakarta.inject.Inject
import kotlinx.coroutines.delay

class UserRepositoryImpl @Inject constructor(): UserRepository {

    override suspend fun getUsers(): List<User> {
        delay(1000)

        return listOf(
            User(1, "John"),
            User(2, "Kate"),
            User(3, "Alex")
        )
    }
}