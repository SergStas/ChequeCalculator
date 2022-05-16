package com.sergstas.domain.models.auth

sealed class RegistrationResult {
    object Success: RegistrationResult()

    sealed class Error: RegistrationResult() {
        object UserNameIsOccupied: Error()
        object Unknown: Error()
    }
}
