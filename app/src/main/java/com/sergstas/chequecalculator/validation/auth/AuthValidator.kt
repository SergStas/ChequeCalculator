package com.sergstas.chequecalculator.validation.auth

import javax.inject.Inject

class AuthValidator @Inject constructor(): IAuthValidator {
    override fun validateUserName(value: String) =
        if (value.isEmpty()) IAuthValidator.Result.Error.UserNameIsEmpty
        else IAuthValidator.Result.Ok

    override fun validateTelegramUserName(value: String) =
        when {
            value.isEmpty() -> IAuthValidator.Result.Error.TelegramIsEmpty
            value[0] != '@' -> IAuthValidator.Result.Error.TelegramInvalidStartChar
            else -> IAuthValidator.Result.Ok
        }
}