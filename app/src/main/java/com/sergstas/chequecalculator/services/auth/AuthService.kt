package com.sergstas.chequecalculator.services.auth

import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationResult
import javax.inject.Inject

class AuthService @Inject constructor(): IAuthService {
    override suspend fun getLoggedInUser(): UserData? {
        return null
    }

    override suspend fun login(username: String): LoginResult {
        return LoginResult.Error.Unknown
    }

    override suspend fun register(username: String, telegram: String): RegistrationResult {
        return RegistrationResult.Error.Unknown
    }

    override suspend fun logout(username: String): Boolean {
        return false
    }
}