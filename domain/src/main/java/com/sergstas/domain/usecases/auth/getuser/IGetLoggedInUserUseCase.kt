package com.sergstas.domain.usecases.auth.getuser

import com.sergstas.domain.models.UserData

interface IGetLoggedInUserUseCase {
    suspend operator fun invoke(): UserData?
}