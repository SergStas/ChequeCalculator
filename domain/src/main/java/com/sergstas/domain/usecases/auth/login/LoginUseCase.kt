package com.sergstas.domain.usecases.auth.login

import com.sergstas.domain.repository.IUserRepository
import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.auth.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): ILoginUseCase {
    override suspend fun invoke(params: LoginParams): LoginResult =
        userRepository.loginUser(params)
}