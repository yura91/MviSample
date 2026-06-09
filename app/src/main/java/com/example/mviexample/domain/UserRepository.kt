package com.example.mviexample.domain

import com.example.mviexample.data.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}