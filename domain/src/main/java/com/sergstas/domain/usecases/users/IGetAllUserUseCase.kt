package com.sergstas.domain.usecases.users

import com.sergstas.domain.models.UserData

interface IGetAllUserUseCase {
    suspend operator fun invoke(): List<UserData>
}