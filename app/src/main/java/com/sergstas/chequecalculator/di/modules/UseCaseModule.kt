package com.sergstas.chequecalculator.di.modules

import com.sergstas.domain.usecases.auth.getuser.GetLoggedInUserUseCase
import com.sergstas.domain.usecases.auth.getuser.IGetLoggedInUserUseCase
import com.sergstas.domain.usecases.auth.login.ILoginUseCase
import com.sergstas.domain.usecases.auth.login.LoginUseCase
import com.sergstas.domain.usecases.auth.logout.ILogoutUseCase
import com.sergstas.domain.usecases.auth.logout.LogoutUseCase
import com.sergstas.domain.usecases.auth.registration.IRegisterUseCase
import com.sergstas.domain.usecases.auth.registration.RegisterUseCase
import com.sergstas.domain.usecases.calculate.CalculateResultUseCase
import com.sergstas.domain.usecases.calculate.ICalculateResultUseCase
import com.sergstas.domain.usecases.history.GetHistoryUseCase
import com.sergstas.domain.usecases.history.IGetHistoryUseCase
import com.sergstas.domain.usecases.users.GetAllUsersUseCase
import com.sergstas.domain.usecases.users.IGetAllUserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds abstract fun bindLogoutUseCase(logoutUseCase: LogoutUseCase): ILogoutUseCase
    @Binds abstract fun bindGetUserUseCase(getUserUseCase: GetLoggedInUserUseCase): IGetLoggedInUserUseCase
    @Binds abstract fun bindLoginUseCase(logoutUseCase: LoginUseCase): ILoginUseCase
    @Binds abstract fun bindRegisterUseCase(registerUseCase: RegisterUseCase): IRegisterUseCase
    @Binds abstract fun bindGetResultUseCase(resultUseCase: CalculateResultUseCase): ICalculateResultUseCase
    @Binds abstract fun bindHistoryUseCase(logoutUseCase: GetHistoryUseCase): IGetHistoryUseCase
    @Binds abstract fun bindAllUsersUseCase(logoutUseCase: GetAllUsersUseCase): IGetAllUserUseCase
}