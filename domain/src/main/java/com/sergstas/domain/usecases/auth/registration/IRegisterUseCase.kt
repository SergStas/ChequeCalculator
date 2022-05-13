package com.sergstas.domain.usecases.auth.registration

import com.sergstas.domain.models.auth.RegistrationResult
import com.sergstas.domain.models.auth.RegistrationParams

interface IRegisterUseCase {
    suspend operator fun invoke(params: RegistrationParams): RegistrationResult
}