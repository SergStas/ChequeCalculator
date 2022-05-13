package com.sergstas.chequecalculator.validation

interface INewEventValidator {
    fun validateDate(token: String): Boolean
}