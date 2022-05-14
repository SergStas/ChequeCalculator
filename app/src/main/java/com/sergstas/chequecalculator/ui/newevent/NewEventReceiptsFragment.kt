package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventReceiptsBinding
import com.sergstas.chequecalculator.databinding.ItemSingleButtonTitleBinding
import com.sergstas.chequecalculator.ui.newevent.models.SingleButtonItem
import com.sergstas.chequecalculator.ui.newevent.viewholders.SingleButtonItemViewHolder
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.rv.AbstractAdapter
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import com.sergstas.domain.models.SessionData

class NewEventReceiptsFragment: Fragment(R.layout.fragment_new_event_receipts) {
    private val binding by viewBinding(FragmentNewEventReceiptsBinding::bind)
    private val viewModel by activityViewModels<NewEventViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }
    private lateinit var adapter: AbstractAdapter<SingleButtonItem, SingleButtonItemViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setAdapter()
        subscribe()
    }

    private fun subscribe() = with(viewModel) {
        members.observe(viewLifecycleOwner) { updateAddButton() }

        receipts.observe(viewLifecycleOwner) {
            val mapped = it?.map(this@NewEventReceiptsFragment::toListItem) ?: return@observe
            if (mapped != adapter.currentList) {
                adapter.submitList(mapped)
            }
            updateFinishButton()
        }
    }

    private fun updateAddButton() {
        binding.neReceiptsBAdd.isEnabled = !viewModel.members.value.isNullOrEmpty()
    }

    private fun updateFinishButton() {
        val value = viewModel.receipts.value?.isNotEmpty() ?: false
        binding.neReceiptsBFinish.isEnabled = value
    }

    private fun setAdapter() {
        adapter = AbstractAdapter.create {
            SingleButtonItemViewHolder(
                ItemSingleButtonTitleBinding.inflate(requireActivity().layoutInflater, it, false),
            )
        }.apply { bindToRecycler(binding.neReceiptsRvReceipts) }
    }

    private fun setView() = with(binding) {
        neReceiptsBAdd.isEnabled = true
        neReceiptsBAdd.setOnClickListener { toNewReceiptPage() }
        updateAddButton()
        updateFinishButton()
    }

    private fun toListItem(receipt: SessionData.ReceiptData) =
        SingleButtonItem(
            title = "${receipt.price} - ${receipt.name}",
            buttonText = getString(R.string.button_remove),
            onButtonPressed = { viewModel.deleteReceipt(receipt) },
            onTouch = { toNewReceiptPage(receipt) }
        )

    private fun toNewReceiptPage(receipt: SessionData.ReceiptData? = null) =
        findNavController().navigate(
            resId = R.id.action_newEventReceiptsFragment_to_newEventNewReceiptFragment,
            args = NewEventNewReceiptFragmentArgs(receipt).toBundle()
        )
}