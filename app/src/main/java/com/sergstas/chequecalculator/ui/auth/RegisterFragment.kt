package com.sergstas.chequecalculator.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentRegisterBinding
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.validation.auth.IAuthValidator
import com.sergstas.chequecalculator.vm.auth.AuthViewModel
import com.sergstas.domain.models.auth.RegistrationResult

class RegisterFragment: Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by activityViewModels<AuthViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    private fun subscribe() = with(viewModel) {
        validationResult.observe(viewLifecycleOwner) {
            val msg = when (it) {
                IAuthValidator.Result.Error.UserNameIsEmpty ->
                    getString(R.string.login_toast_empty_login)
                IAuthValidator.Result.Error.TelegramIsEmpty ->
                    getString(R.string.register_toast_empty_telegram)
                IAuthValidator.Result.Error.TelegramInvalidStartChar ->
                    getString(R.string.register_toast_telegram_tag_char)
                is IAuthValidator.Result.Ok -> null
                else -> return@observe
            }
            setError(msg)
        }
        registrationResult.observe(viewLifecycleOwner) {
            if (it is RegistrationResult.Error) {
                val msg =
                    if (it is RegistrationResult.Error.UserNameIsOccupied) getString(R.string.login_toast_no_user)
                    else getString(R.string.login_toast_unknown)
                setError(msg)
            } else {
                toMenu()
            }
        }
        loading.observe(viewLifecycleOwner) {
            binding.registerPbLoading.isVisible = it
        }
        loggedInUser.observe(viewLifecycleOwner) {
            if (it != null) toMenu()
        }
    }

    private fun setView() = with(binding) {
        registerTvLogin.setOnClickListener { toLoginPage() }
        registerBRegister.setOnClickListener {
            viewModel.register(registerEtLogin.text.toString(), registerEtTelegram.text.toString())
        }
    }

    private fun setError(msg: String?) {
        binding.registerTvError.isVisible = msg != null
        binding.registerTvError.text = msg ?: ""
    }

    private fun toMenu() =
        findNavController().navigate(R.id.action_registerFragment_to_nav_menu)

    private fun toLoginPage() =
        findNavController().popBackStack()
}