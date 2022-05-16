package com.sergstas.data.storage

import com.sergstas.domain.models.UserData

interface IUserStorage {
    suspend fun loginUser(user: UserData)

    suspend fun logoutUser()

    suspend fun getUser(): UserData?
}