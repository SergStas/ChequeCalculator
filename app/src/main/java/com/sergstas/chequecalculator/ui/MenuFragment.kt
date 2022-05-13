package com.sergstas.chequecalculator.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentMenuBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {
    private val binding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuBNewEvent.setOnClickListener { toNewEvent() }
    }

    private fun toNewEvent() =
        findNavController().navigate(
            R.id.action_menuFragment_to_newEventFragment,
        )
}