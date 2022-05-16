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
        val call = api.login(params.username)
        val response = call.execute()
        val body = response.body()
        return when {
            response.isSuccessful -> body?.run { LoginResult.Success(this) } ?: LoginResult.Error.Unknown
            response.code() == 404 -> LoginResult.Error.UserNotFound
            else -> LoginResult.Error.Unknown
        }
    }

    override suspend fun register(params: RegistrationParams) =
        when(api.register(params).status) {
            0 -> RegistrationResult.Success
            1 -> RegistrationResult.Error.UserNameIsOccupied
            else -> RegistrationResult.Error.Unknown
        }

    override suspend fun getAllUsers(): List<UserData> =
        api.getAllUsers()

    override suspend fun logout() =
        userStorage.logoutUser()
}