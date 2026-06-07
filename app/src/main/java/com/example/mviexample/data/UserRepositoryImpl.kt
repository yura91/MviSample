package com.example.mviexample.data

import kotlinx.coroutines.delay

class UserRepositoryImpl: UserRepository {

    override suspend fun getUsers(): List<User> {
        delay(1000)

        return listOf(
            User(1, "John"),
            User(2, "Kate"),
            User(3, "Alex")
        )
    }
}