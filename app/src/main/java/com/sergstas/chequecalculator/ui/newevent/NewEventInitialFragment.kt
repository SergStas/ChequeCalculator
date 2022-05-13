package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventInitialBinding

class NewEventInitialFragment: Fragment(R.layout.fragment_new_event_initial) {
    private val binding by viewBinding(FragmentNewEventInitialBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() = with(binding) {
        neInitialBNext.setOnClickListener { nextPage() }
    }

    private fun nextPage() =
        findNavController().navigate(
            R.id.action_newEventInitialFragment_to_newEventMembersFragment,
        )
}