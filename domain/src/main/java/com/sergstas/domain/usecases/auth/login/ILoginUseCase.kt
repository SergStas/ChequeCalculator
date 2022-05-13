package com.sergstas.domain.usecases.auth.login

import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.LoginParams

interface ILoginUseCase {
    suspend operator fun invoke(params: LoginParams): LoginResult
}