package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventInitialBinding
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.extensions.setOnNewValueListener
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel

class NewEventInitialFragment: Fragment(R.layout.fragment_new_event_initial) {
    private val binding by viewBinding(FragmentNewEventInitialBinding::bind)
    private val viewModel by viewModels<NewEventViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        subscribe()
    }

    private fun subscribe() = with(viewModel) {
        eventName.observe(viewLifecycleOwner) {
            if (it != binding.neInitialEtName.text.toString()) {
                binding.neInitialEtName.setText(it)
            }
            updateNextButton()
        }

        eventDate.observe(viewLifecycleOwner) {
            if (it != binding.neInitialEtDate.text.toString()) {
                binding.neInitialEtDate.setText(it)
            }
            updateNextButton()
        }
    }

    private fun setView() = with(binding) {
        neInitialBNext.setOnClickListener { nextPage() }
        neInitialEtDate.setOnNewValueListener(viewModel::setEventDate)
        neInitialEtName.setOnNewValueListener(viewModel::setEventName)
        updateNextButton()
    }

    private fun updateNextButton() {
        val value = viewModel.eventDate.value != null && viewModel.eventName.value?.isNotEmpty() ?: false
        binding.neInitialBNext.isEnabled = value
    }

    private fun nextPage() =
        findNavController().navigate(
            R.id.action_newEventInitialFragment_to_newEventMembersFragment,
        )
}