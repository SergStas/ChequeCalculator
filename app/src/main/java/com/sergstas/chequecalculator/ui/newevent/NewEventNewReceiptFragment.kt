package com.sergstas.chequecalculator.ui.newevent

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.FragmentNewEventNewReceiptBinding
import com.sergstas.chequecalculator.databinding.ItemPositionBinding
import com.sergstas.chequecalculator.ui.newevent.models.PositionItem
import com.sergstas.chequecalculator.ui.newevent.viewholders.PositionItemViewHolder
import com.sergstas.chequecalculator.util.extensions.findAppComponent
import com.sergstas.chequecalculator.util.extensions.setOnNewValueListener
import com.sergstas.chequecalculator.util.rv.AbstractAdapter
import com.sergstas.chequecalculator.util.spinner.DefaultSpinnerAdapter
import com.sergstas.chequecalculator.util.spinner.onItemSelected
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import com.sergstas.domain.models.SessionData
import com.sergstas.domain.models.UserData

class NewEventNewReceiptFragment: Fragment(R.layout.fragment_new_event_new_receipt) {
    private val binding by viewBinding(FragmentNewEventNewReceiptBinding::bind)
    private val args by navArgs<NewEventNewReceiptFragmentArgs>()
    private val viewModel by activityViewModels<NewEventViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }
    private lateinit var adapter: AbstractAdapter<PositionItem, PositionItemViewHolder>

    private val totalPrice get() = viewModel.receiptPositions.value?.sumOf { it.position.price } ?: 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgReceipt()
        setAdapter()
        setView()
        subscribe()
    }

    private fun setAdapter() {
        adapter = AbstractAdapter.create { PositionItemViewHolder(
            ItemPositionBinding.inflate(requireActivity().layoutInflater, it, false),
        ) }.apply { bindToRecycler(binding.neNrRvPositions) }
    }

    private fun subscribe() {
        viewModel.members.observe(viewLifecycleOwner, ::submitSpinner)
        viewModel.receiptName.observe(viewLifecycleOwner, ::updateName)
        viewModel.receiptPayer.observe(viewLifecycleOwner, ::updatePayer)
        viewModel.receiptPositions.observe(viewLifecycleOwner, ::updatePositions)
    }

    private fun updatePositions(list: List<NewEventViewModel.PositionWithMessage>?) {
        updatePrice()
        val mapped = list?.map { toPositionItem(it.position) } ?: return
        if (mapped == adapter.currentList) return
        adapter.submitList(mapped)
        updateFinishButton()
    }

    private fun updatePayer(payer: UserData?) {
        val index = viewModel.members.value?.indexOf(payer) ?: return
        if (index < 0) return
        binding.neNrSpinnerPayer.setSelection(index)
        updateFinishButton()
    }

    private fun updateName(name: String?) {
        if (name == binding.neNrEtName.text.toString()) return
        binding.neNrEtName.setText(name)
        updateFinishButton()
    }

    private fun setArgReceipt() =
        viewModel.setEditReceipt(args.receipt)

    private fun setView() = with(binding) {
        neNrEtName.setOnNewValueListener(viewModel::updateEditReceiptName)
        neNrBFinish.setOnClickListener {
            if (args.receipt == null) {
                viewModel.saveReceipt()
            } else {
                viewModel.replaceReceipt(args.receipt!!)
            }
            back()
        }
        neNrBAdd.setOnClickListener { viewModel.addPosition() }
        neNrSpinnerPayer.onItemSelected(viewModel::updateEditReceiptPayer)
        submitVmValues()
    }

    private fun submitVmValues() {
        submitSpinner(viewModel.members.value)
        updatePositions(viewModel.receiptPositions.value)
        updateName(viewModel.receiptName.value)
        updatePayer(viewModel.receiptPayer.value)
    }

    private fun updateFinishButton() {
        val name = viewModel.receiptName.value?.isNotEmpty() ?: false
        val payer = viewModel.receiptPayer.value != null
        val positions = viewModel.receiptPositions.value?.isNotEmpty() ?: false
        binding.neNrBFinish.isEnabled = name && payer && positions
    }

    private fun submitSpinner(members: List<UserData>?) {
        val mapped = members?.map { it.name } ?: emptyList()
        binding.neNrSpinnerPayer.adapter = DefaultSpinnerAdapter(mapped)
        updateVisibility()
    }

    private fun updateVisibility() {
        val list = viewModel.members.value ?: emptyList()
        binding.neNrTvNoMembers.isVisible = list.isEmpty()
        binding.neNrSpinnerPayer.isVisible = list.isNotEmpty()
    }

    private fun updatePrice() {
        val token = getString(R.string.ne_nr_tv_price_ph).format(totalPrice.toFloat())
        if (binding.neNrTvPrice.text == token) return
        binding.neNrTvPrice.text = token
    }

    private fun toPositionItem(data: SessionData.PositionData) =
        PositionItem.fromPositionData(
            data = data,
            members = viewModel.members.value ?: emptyList(),
            onRemove = viewModel::deletePosition,
            onTitleEdited = viewModel::editPositionTitle,
            onPriceEdited = viewModel::editPositionPrice,
            onRemovePart = viewModel::removePart,
            onPartEdited = viewModel::editPart,
            onMemberIncluded = viewModel::addPart,
        )

    private fun back() {
        findNavController().apply {
            popBackStack()
            navigate(R.id.newEventReceiptsFragment)
        }
    }
}
