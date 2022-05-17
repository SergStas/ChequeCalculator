package com.sergstas.domain.usecases.auth.logout

import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): ILogoutUseCase {
    override suspend fun invoke() =
        userRepository.logout()
}