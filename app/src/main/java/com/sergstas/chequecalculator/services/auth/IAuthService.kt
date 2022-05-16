package com.sergstas.chequecalculator.services.auth

import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationResult

interface IAuthService {
    suspend fun getLoggedInUser(): UserData?

    suspend fun login(username: String): LoginResult

    suspend fun register(username: String, telegram: String): RegistrationResult

    suspend fun logout()
}