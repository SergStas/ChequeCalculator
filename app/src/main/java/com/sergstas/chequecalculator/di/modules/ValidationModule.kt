package com.sergstas.chequecalculator.di.modules

import com.sergstas.chequecalculator.validation.auth.AuthValidator
import com.sergstas.chequecalculator.validation.auth.IAuthValidator
import com.sergstas.chequecalculator.validation.newevent.INewEventValidator
import com.sergstas.chequecalculator.validation.newevent.IPositionValidator
import com.sergstas.chequecalculator.validation.newevent.NewEventValidator
import com.sergstas.chequecalculator.validation.newevent.PositionValidator
import dagger.Binds
import dagger.Module

@Module
abstract class ValidationModule {
    @Binds abstract fun bindNewEventValidator(validator: NewEventValidator): INewEventValidator
    @Binds abstract fun bindPositionValidator(validator: PositionValidator): IPositionValidator
    @Binds abstract fun bindAuthValidator(validator: AuthValidator): IAuthValidator
}