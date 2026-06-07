package com.example.mviexample.data

interface UserRepository {
    suspend fun getUsers(): List<User>
}