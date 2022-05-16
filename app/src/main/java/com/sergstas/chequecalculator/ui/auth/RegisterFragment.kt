package com.sergstas.chequecalculator.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentRegisterBinding
import com.sergstas.chequecalculator.services.auth.IAuthService
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.extensions.toast
import com.sergstas.domain.models.auth.RegistrationResult
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

class RegisterFragment: Fragment(R.layout.fragment_register) {
    @Inject lateinit var authService: IAuthService

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().findAppComponent().inject(this)
        with(binding) {
            registerTvLogin.setOnClickListener { toLoginPage() }
            registerBRegister.setOnClickListener { register() }
        }
    }

    private fun register() {
        val login = binding.registerEtLogin.text.toString()
        val telegram = binding.registerEtTelegram.text.toString()
        val toastMsgId = when {
            login.isEmpty() -> R.string.register_toast_empty_login
            telegram.isEmpty() -> R.string.register_toast_empty_telegram
            telegram[0] != '@' -> R.string.register_toast_telegram_tag_char
            else -> null
        }
        if (toastMsgId != null) requireContext().toast(getString(toastMsgId))
        else {
            MainScope().launch {
                val result = authService.register(login, telegram)
                if (result !is RegistrationResult.Error) toMenu()
                else {
                    val errorMsg = getString(when (result) {
                        RegistrationResult.Error.UserNameIsOccupied -> R.string.register_toast_login_is_occuppied
                        RegistrationResult.Error.Unknown -> R.string.login_toast_unknown
                    })
                    requireContext().toast(errorMsg)
                }
            }
        }
    }

    private fun toMenu() =
        findNavController().navigate(R.id.action_registerFragment_to_nav_menu)

    private fun toLoginPage() =
        findNavController().popBackStack()
}