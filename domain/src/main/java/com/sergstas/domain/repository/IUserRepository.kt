package com.sergstas.domain.repository

import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.models.auth.RegistrationResult

interface IUserRepository {
    suspend fun getLoggedInUser(): UserData?

    suspend fun loginUser(params: LoginParams): LoginResult

    suspend fun register(params: RegistrationParams): RegistrationResult

    suspend fun getAllUsers(): List<UserData>
}