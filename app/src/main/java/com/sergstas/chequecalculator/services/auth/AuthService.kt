package com.sergstas.chequecalculator.services.auth

import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class AuthService @Inject constructor(
    private val userRepository: IUserRepository,
): IAuthService {
    override suspend fun getLoggedInUser() =
        userRepository.getLoggedInUser()

    override suspend fun login(username: String) =
        userRepository.loginUser(LoginParams(username))

    override suspend fun register(username: String, telegram: String) =
        userRepository.register(RegistrationParams(username, telegram))

    override suspend fun logout() =
        userRepository.logout()
}