package com.sergstas.domain.usecases.users

import com.sergstas.domain.models.UserData
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): IGetAllUserUseCase {
    override suspend fun invoke(): List<UserData> =
        userRepository.getAllUsers()
}