package com.sergstas.chequecalculator.di.modules

import com.sergstas.chequecalculator.validation.INewEventValidator
import com.sergstas.chequecalculator.validation.NewEventValidator
import dagger.Binds
import dagger.Module

@Module
abstract class ValidationModule {
    @Binds
    abstract fun bindNewEventValidator(validator: NewEventValidator): INewEventValidator
}