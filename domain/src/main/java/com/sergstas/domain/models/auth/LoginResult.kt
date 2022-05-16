package com.sergstas.domain.models.auth

import com.sergstas.domain.models.UserData

sealed class LoginResult {
    data class Success(val user: UserData): LoginResult()

    sealed class Error: LoginResult() {
        object UserNotFound: Error()
        object Unknown: Error()
    }
}