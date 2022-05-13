package com.sergstas.chequecalculator.di.modules

import com.sergstas.chequecalculator.di.modules.vm.ViewModelBindsModule
import dagger.Module

@Module(includes = [
    ViewModelBindsModule::class,
])
class AppModule