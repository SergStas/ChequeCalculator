package com.sergstas.chequecalculator.vm.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergstas.chequecalculator.services.auth.IAuthService
import com.sergstas.chequecalculator.validation.auth.IAuthValidator
import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.LoginResult
import com.sergstas.domain.models.auth.RegistrationResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authValidator: IAuthValidator,
    private val authService: IAuthService,
): ViewModel() {
    val loggedInUser: LiveData<UserData?> get() = _loggedInUser
    private val _loggedInUser = MutableLiveData<UserData?>()

    val loginResult: LiveData<LoginResult?> get() = _loginResult
    private val _loginResult = MutableLiveData<LoginResult?>()

    val registrationResult: LiveData<RegistrationResult?> get() = _registrationResult
    private val _registrationResult = MutableLiveData<RegistrationResult?>()

    val validationResult: LiveData<IAuthValidator.Result?> get() = _validationResult
    private val _validationResult = MutableLiveData<IAuthValidator.Result?>()

    val loading: LiveData<Boolean> get() = _loading
    private val _loading = MutableLiveData(false)

    fun login(username: String) {
        _loading.value = true
        _validationResult.value = authValidator.validateUserName(username)
        if (_validationResult.value == IAuthValidator.Result.Ok) {
            viewModelScope.launch(getExceptionHandler(login = true)) {
                _loginResult.value = authService.login(username)
            }
        }
        _loading.value = false
    }

    fun register(username: String, telegram: String) {
        _loading.value = true
        val usernameValid = authValidator.validateUserName(username)
        val telegramValid = authValidator.validateTelegramUserName((telegram))
        _validationResult.value = listOf(usernameValid, telegramValid)
            .firstOrNull { it is IAuthValidator.Result.Error } ?: IAuthValidator.Result.Ok
        if (_validationResult.value == IAuthValidator.Result.Ok) {
            viewModelScope.launch(getExceptionHandler(registration = true)) {
                _registrationResult.value = authService.register(username, telegram)
            }
        }
        _loading.value = false
    }

    fun checkIfAuthed() {
        viewModelScope.launch(getExceptionHandler()) {
            _loggedInUser.value = authService.getLoggedInUser()
        }
    }

    private fun getExceptionHandler(
        login: Boolean = false,
        registration: Boolean = false,
    ) = CoroutineExceptionHandler { _, _ ->
        if (login) _loginResult.value = LoginResult.Error.Unknown
        if (registration) _registrationResult.value = RegistrationResult.Error.Unknown
        _loading.value = false
    }
}