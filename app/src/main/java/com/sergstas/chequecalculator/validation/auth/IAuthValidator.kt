package com.sergstas.chequecalculator.validation.auth

interface IAuthValidator {
    fun validateUserName(value: String): Result
    fun validateTelegramUserName(value: String): Result

    sealed class Result {
        object Ok: Result()

        sealed class Error: Result() {
            object UserNameIsEmpty: Error()
            object TelegramIsEmpty: Error()
            object TelegramInvalidStartChar: Error()
        }
    }
}