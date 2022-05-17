package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.extensions.toast
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel

class NewEventFragment: Fragment(R.layout.fragment_new_event) {
    private val viewModel by activityViewModels<NewEventViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lastError.observe(viewLifecycleOwner) {
            requireContext().toast(getString(when(it ?: return@observe) {
                NewEventViewModel.Error.USER_REQUEST_FAILED -> R.string.error_get_all_user_fail
            }))
        }
    }
}