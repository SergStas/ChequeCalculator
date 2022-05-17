package com.sergstas.chequecalculator.validation.newevent

interface INewEventValidator {
    fun validateDate(token: String): Boolean
}