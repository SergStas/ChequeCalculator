package com.sergstas.chequecalculator.validation

import javax.inject.Inject

class NewEventValidator @Inject constructor(): INewEventValidator {
    override fun validateDate(token: String) =
        token.matches(Regex("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}"))
}