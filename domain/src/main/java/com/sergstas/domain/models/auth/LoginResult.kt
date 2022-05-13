package com.sergstas.domain.models.auth

import com.sergstas.domain.models.UserData

sealed class LoginResult {
    data class Success(val user: UserData): LoginResult()

    object UserNotFound: LoginResult()

    data class Error(val throwable: Throwable): LoginResult()
}