package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventReceiptsBinding

class NewEventReceiptsFragment: Fragment(R.layout.fragment_new_event_receipts) {
    private val binding by viewBinding(FragmentNewEventReceiptsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() = with(binding) {
        neReceiptsBAdd.isEnabled = true
        neReceiptsBAdd.setOnClickListener { toNewReceiptPage() }
    }

    private fun toNewReceiptPage() =
        findNavController().navigate(
            R.id.action_newEventReceiptsFragment_to_newEventNewReceiptFragment,
        )
}