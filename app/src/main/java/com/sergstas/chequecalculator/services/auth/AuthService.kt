package com.sergstas.chequecalculator.services.auth

import com.sergstas.domain.models.auth.LoginParams
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.domain.repository.IUserRepository
import com.sergstas.domain.usecases.auth.getuser.IGetLoggedInUserUseCase
import com.sergstas.domain.usecases.auth.login.ILoginUseCase
import com.sergstas.domain.usecases.auth.logout.ILogoutUseCase
import com.sergstas.domain.usecases.auth.registration.IRegisterUseCase
import javax.inject.Inject

class AuthService @Inject constructor(
    private val loginUseCase: ILoginUseCase,
    private val getLoggedInUserUseCase: IGetLoggedInUserUseCase,
    private val registerUseCase: IRegisterUseCase,
    private val logoutUseCase: ILogoutUseCase,
): IAuthService {
    override suspend fun getLoggedInUser() =
        getLoggedInUserUseCase()

    override suspend fun login(username: String) =
        loginUseCase(LoginParams(username))

    override suspend fun register(username: String, telegram: String) =
        registerUseCase(RegistrationParams(username, telegram))

    override suspend fun logout() =
        logoutUseCase()
}