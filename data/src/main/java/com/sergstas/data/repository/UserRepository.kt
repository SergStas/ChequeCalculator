package com.sergstas.data.repository

import com.sergstas.data.storage.IUserStorage
import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.models.auth.RegistrationResult
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userStorage: IUserStorage,
): IUserRepository {
    override suspend fun getLoggedInUser() =
        userStorage.getUser()

    override suspend fun loginUser(params: LoginParams): LoginResult {
        return LoginResult.Error.UserNotFound
    }

    override suspend fun register(params: RegistrationParams): RegistrationResult {
        return RegistrationResult.Error.Unknown
    }

    override suspend fun getAllUsers(): List<UserData> {
        return listOf("Aaa Bbb", "Fff Eee", "Ggg Hhh").map { UserData(it) }
    }

    override suspend fun logout() =
        userStorage.logoutUser()
}