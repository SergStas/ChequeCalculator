package com.sergstas.domain.usecases.auth.logout

interface ILogoutUseCase {
    suspend operator fun invoke()
}