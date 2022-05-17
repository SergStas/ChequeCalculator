package com.sergstas.data.repository

import com.sergstas.data.storage.IUserStorage
import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.models.auth.RegistrationResult
import com.sergstas.domain.repository.IUserRepository
import com.sergstas.network.IApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userStorage: IUserStorage,
    private val api: IApi,
): IUserRepository {
    override suspend fun getLoggedInUser() =
        userStorage.getUser()

    override suspend fun loginUser(params: LoginParams): LoginResult {
        val response = api.login(params.username)
        val result = when {
            response.isSuccessful -> response.body()?.run { LoginResult.Success(this) }
                ?: LoginResult.Error.Unknown
            response.code() == 404 -> LoginResult.Error.UserNotFound
            else -> LoginResult.Error.Unknown
        }
        if (result is LoginResult.Success) {
            userStorage.loginUser(result.user)
        }
        return result
    }

    override suspend fun register(params: RegistrationParams): RegistrationResult {
        val result = when(api.register(params).status) {
            0 -> RegistrationResult.Success
            1 -> RegistrationResult.Error.UserNameIsOccupied
            else -> RegistrationResult.Error.Unknown
        }
        if (result is RegistrationResult.Success) {
            userStorage.loginUser(UserData(params.username))
        }
        return result
    }

    override suspend fun getAllUsers(): List<UserData> =
        api.getAllUsers()

    override suspend fun logout() =
        userStorage.logoutUser()
}