package com.sergstas.chequecalculator.validation.newevent

import com.sergstas.chequecalculator.validation.newevent.IPositionValidator.Result.Invalid.Error
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import javax.inject.Inject
import kotlin.math.abs

class PositionValidator @Inject constructor(): IPositionValidator {
    override fun validate(position: NewEventViewModel.IndexedPosition): IPositionValidator.Result {
        val errors = mutableListOf<Error>()
        if (position.name.isEmpty()) {
            errors.add(Error.EmptyName)
        }
        if (position.price < 1e-2) {
            errors.add(Error.PriceNotPositive)
        }
        if (position.parts.isEmpty()) {
            errors.add(Error.NoParts)
        }
        if (position.parts.size != position.parts.distinctBy { it.user }.size) {
            errors.add(Error.DuplicatedUsers)
        }
        position.parts.forEach {
            if (it.value < 1e-2) {
                errors.add(Error.PartNotPositive)
            }
        }
        if (abs(position.parts.sumOf { it.value } - 100.0) >= 1e-2) {
            errors.add(Error.PartsSumInvalid)
        }
        return if (errors.isEmpty()) IPositionValidator.Result.Ok
            else IPositionValidator.Result.Invalid(errors)
    }
}