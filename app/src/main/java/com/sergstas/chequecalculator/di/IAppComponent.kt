package com.sergstas.chequecalculator.di

import com.sergstas.chequecalculator.di.modules.AppModule
import com.sergstas.chequecalculator.di.modules.vm.ViewModelFactory
import com.sergstas.chequecalculator.ui.auth.LoginFragment
import com.sergstas.chequecalculator.ui.auth.RegisterFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface IAppComponent {
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegisterFragment)

    fun viewModelFactory(): ViewModelFactory
}