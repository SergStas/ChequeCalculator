package com.sergstas.chequecalculator.di.modules

import com.sergstas.chequecalculator.di.modules.vm.DataModule
import com.sergstas.chequecalculator.di.modules.vm.ViewModelModule
import dagger.Module

@Module(includes = [
    ViewModelModule::class,
    ValidationModule::class,
    DataModule::class,
])
class AppModule