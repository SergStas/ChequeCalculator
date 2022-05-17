package com.sergstas.chequecalculator.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentLoginBinding
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.validation.auth.IAuthValidator
import com.sergstas.chequecalculator.vm.auth.AuthViewModel
import com.sergstas.domain.models.auth.LoginResult

class LoginFragment: Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by activityViewModels<AuthViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkIfAuthed()
    }

    private fun setView() = with(binding) {
        loginBLogin.setOnClickListener { viewModel.login(loginEtLogin.text.toString()) }
        loginTvRegister.setOnClickListener { toRegisterPage() }
    }

    private fun subscribe() = with(viewModel) {
        validationResult.observe(viewLifecycleOwner) {
            val msg = when (it) {
                IAuthValidator.Result.Error.UserNameIsEmpty -> getString(R.string.login_toast_empty_login)
                is IAuthValidator.Result.Ok -> null
                else -> return@observe
            }
            setError(msg)
        }
        loginResult.observe(viewLifecycleOwner) {
            if (it is LoginResult.Error) {
                val msg =
                    if (it is LoginResult.Error.UserNotFound) getString(R.string.login_toast_no_user)
                    else getString(R.string.login_toast_unknown)
                setError(msg)
            } else {
                toMenu()
            }
        }
        loading.observe(viewLifecycleOwner) {
            binding.loginPbLoading.isVisible = it
        }
        loggedInUser.observe(viewLifecycleOwner) {
            if (it != null) toMenu()
        }
    }

    private fun setError(msg: String?) {
        binding.loginTvError.isVisible = msg != null
        binding.loginTvError.text = msg ?: ""
    }

    private fun toRegisterPage() =
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

    private fun toMenu() =
        findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
}