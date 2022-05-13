package com.sergstas.domain.usecases.auth.registration

import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.models.auth.RegistrationResult
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): IRegisterUseCase {
    override suspend fun invoke(params: RegistrationParams): RegistrationResult =
        userRepository.register(params)
}