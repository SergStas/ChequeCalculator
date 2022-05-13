package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventMembersBinding

class NewEventMembersFragment: Fragment(R.layout.fragment_new_event_members) {
    private val binding by viewBinding(FragmentNewEventMembersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            neMembersBNext.setOnClickListener { nextPage() }
        }
    }

    private fun nextPage() =
        findNavController().popBackStack()
}