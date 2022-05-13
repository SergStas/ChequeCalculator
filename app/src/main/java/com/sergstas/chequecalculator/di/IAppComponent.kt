package com.sergstas.chequecalculator.di

import com.sergstas.chequecalculator.MainActivity
import com.sergstas.chequecalculator.di.modules.AppModule
import com.sergstas.chequecalculator.di.modules.vm.ViewModelFactory
import dagger.Component

@Component(modules = [AppModule::class])
interface IAppComponent {
    fun inject(a: MainActivity)

    fun viewModelFactory(): ViewModelFactory
}