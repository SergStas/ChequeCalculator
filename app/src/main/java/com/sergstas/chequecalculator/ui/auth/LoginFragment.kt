package com.sergstas.chequecalculator.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentLoginBinding
import com.sergstas.chequecalculator.services.auth.IAuthService
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.extensions.toast
import com.sergstas.domain.models.auth.LoginResult
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment: Fragment(R.layout.fragment_login) {
    @Inject lateinit var authService: IAuthService

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().findAppComponent().inject(this)
        with(binding) {
            loginBLogin.setOnClickListener { login() }
            loginTvRegister.setOnClickListener { toRegisterPage() }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLogin()
    }

    private fun checkLogin() {
        MainScope().launch {
            val user = authService.getLoggedInUser()
            if (user != null) toMenu()
        }
    }

    private fun login() {
        val login = binding.loginEtLogin.text.toString()
        if (login.isEmpty()) requireContext().toast(getString(R.string.login_toast_empty_login))
        else MainScope().launch {
            val result = authService.login(login)
            if (result is LoginResult.Error) {
                val msg =
                    if (result is LoginResult.Error.UserNotFound) getString(R.string.login_toast_no_user)
                    else getString(R.string.login_toast_unknown)
                requireContext().toast(msg)
            } else {
                toMenu()
            }
        }
    }

    private fun toRegisterPage() =
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

    private fun toMenu() =
        findNavController().navigate(R.id.action_loginFragment_to_nav_menu)
}