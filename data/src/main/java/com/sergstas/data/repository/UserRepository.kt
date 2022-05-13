package com.sergstas.data.repository

import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.models.auth.RegistrationResult
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(): IUserRepository {
    override suspend fun getLoggedInUser(): UserData? {
        return null
    }

    override suspend fun loginUser(params: LoginParams): LoginResult {
        return LoginResult.UserNotFound
    }

    override suspend fun register(params: RegistrationParams): RegistrationResult {
        return RegistrationResult.Error.Unknown
    }

    override suspend fun getAllUsers(): List<UserData> {
        return listOf("Aaa Bbb", "Fff Eee", "Ggg Hhh").map { UserData(it) }
    }
}