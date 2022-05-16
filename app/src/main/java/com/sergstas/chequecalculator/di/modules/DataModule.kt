package com.sergstas.chequecalculator.di.modules

import com.sergstas.data.repository.UserRepository
import com.sergstas.data.storage.IUserStorage
import com.sergstas.data.storage.UserSharedPrefsStorage
import com.sergstas.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): IUserRepository

    @Binds
    abstract fun bindUserStorage(userStorage: UserSharedPrefsStorage): IUserStorage
}