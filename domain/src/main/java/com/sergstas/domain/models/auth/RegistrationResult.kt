package com.sergstas.domain.models.auth

sealed class RegistrationResult(open val code: Code) {
    object Success: RegistrationResult(Code.OK)

    sealed class Error(override val code: Code): RegistrationResult(code) {
        object UserNameIsOccupied: Error(Code.OCCUPIED)
        object UnknownError: Error(Code.UNKNOWN)
    }

    enum class Code(val value: Int) {
        OK(0),
        OCCUPIED(1),
        UNKNOWN(99),
    }
}
