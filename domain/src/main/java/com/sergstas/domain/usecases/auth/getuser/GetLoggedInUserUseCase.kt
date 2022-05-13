package com.sergstas.domain.usecases.auth.getuser

import com.sergstas.domain.models.UserData
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): IGetLoggedInUserUseCase {
    override suspend fun invoke(): UserData? =
        userRepository.getLoggedInUser()
}