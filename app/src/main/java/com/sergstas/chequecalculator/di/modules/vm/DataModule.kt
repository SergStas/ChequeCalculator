package com.sergstas.chequecalculator.di.modules.vm

import com.sergstas.data.repository.UserRepository
import com.sergstas.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): IUserRepository
}