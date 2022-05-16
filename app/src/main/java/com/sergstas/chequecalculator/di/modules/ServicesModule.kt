package com.sergstas.chequecalculator.di.modules

import com.sergstas.chequecalculator.services.auth.AuthService
import com.sergstas.chequecalculator.services.auth.IAuthService
import dagger.Binds
import dagger.Module

@Module
abstract class ServicesModule {
    @Binds
    abstract fun bindAuthService(service: AuthService): IAuthService
}