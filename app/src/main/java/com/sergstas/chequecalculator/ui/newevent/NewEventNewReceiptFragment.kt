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
import com.sergstas.chequecalculator.util.extensions.toast
import com.sergstas.chequecalculator.util.rv.AbstractAdapter
import com.sergstas.chequecalculator.util.spinner.DefaultSpinnerAdapter
import com.sergstas.chequecalculator.util.spinner.onItemSelected
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import com.sergstas.domain.models.UserData

class NewEventNewReceiptFragment: Fragment(R.layout.fragment_new_event_new_receipt) {
    private val binding by viewBinding(FragmentNewEventNewReceiptBinding::bind)
    private val args by navArgs<NewEventNewReceiptFragmentArgs>()
    private val viewModel by activityViewModels<NewEventViewModel> {
        requireContext().findAppComponent().viewModelFactory()
    }
    private lateinit var adapter: AbstractAdapter<PositionItem, PositionItemViewHolder>

    private val totalPrice get() = viewModel.receiptPositions.value?.sumOf { it.price } ?: 0.0

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
        viewModel.receiptPositions.observe(viewLifecycleOwner, ::updatePositions)
        viewModel.receiptValid.observe(viewLifecycleOwner, ::updateReceiptValidation)
    }

    private fun updatePositions(list: List<NewEventViewModel.IndexedPosition>?, check: Boolean = true) {
        updatePrice()
        val mapped = list?.map(::toPositionItem) ?: return
        if (check) {
            if (mapped == adapter.currentList) return
            if (mapped.size == adapter.currentList.size) {
                var areEqual = true
                mapped.forEachIndexed { i, newItem ->
                    val oldItem = adapter.currentList[i]
                    val partSizesAreEqual = newItem.parts.size == oldItem.parts.size
                    val expansionEqual = newItem.isExpanded == oldItem.isExpanded
                    if (!partSizesAreEqual || !expansionEqual) {
                        areEqual = false
                    }
                }
                if (areEqual) return
            }
        }
        adapter.submitList(mapped.toList())
    }

    private fun updatePayer(payer: UserData?) {
        val index = viewModel.members.value?.indexOf(payer) ?: return
        if (index < 0) return
        binding.neNrSpinnerPayer.setSelection(index)
    }

    private fun updateName(name: String?) {
        if (name == binding.neNrEtName.text.toString()) return
        binding.neNrEtName.setText(name)
    }

    private fun setArgReceipt() =
        viewModel.setEditReceipt(args.receipt)

    private fun setView() = with(binding) {
        neNrEtName.setOnNewValueListener(viewModel::updateEditReceiptName)
        neNrBAdd.setOnClickListener { viewModel.addPosition() }
        neNrBFinish.setOnClickListener { finish() }
        neNrBFinish.isEnabled = false
        neNrBValidate.setOnClickListener { viewModel.validateReceipt() }
        neNrSpinnerPayer.onItemSelected(viewModel::updateEditReceiptPayer)
        submitVmValues()
    }

    private fun finish() {
        if (args.receipt == null) {
            viewModel.saveReceipt()
        } else {
            viewModel.replaceReceipt(args.receipt!!)
        }
        back()
    }

    private fun submitVmValues() {
        submitSpinner(viewModel.members.value)
        updatePositions(viewModel.receiptPositions.value)
        updateName(viewModel.receiptName.value)
        updatePayer(viewModel.receiptPayer.value)
    }

    private fun updateReceiptValidation(result: NewEventViewModel.ValidationResult) {
        binding.neNrBFinish.isEnabled = result == NewEventViewModel.ValidationResult.OK
        updatePositions(viewModel.receiptPositions.value, false)
        val message = when(result) {
            NewEventViewModel.ValidationResult.OK              -> getString(R.string.ne_nr_validation_result_ok)
            NewEventViewModel.ValidationResult.INVALID_NAME     -> getString(R.string.ne_nr_validation_result_name)
            NewEventViewModel.ValidationResult.INVALID_PAYER    -> getString(R.string.ne_nr_validation_result_payer)
            NewEventViewModel.ValidationResult.NO_POSITIONS     -> getString(R.string.ne_nr_validation_result_empty)
            NewEventViewModel.ValidationResult.INVALID_PARTS    -> getString(R.string.ne_nr_validation_result_parts)
        }
        requireContext().toast(message)
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

    private fun toPositionItem(data: NewEventViewModel.IndexedPosition) =
        PositionItem.fromPositionData(
            data = data,
            members = viewModel.members.value ?: emptyList(),
            isExpanded = data.isExpanded,
            onExpand = viewModel::expandPosition,
            onRemove = viewModel::deletePosition,
            onTitleEdited = viewModel::editPositionTitle,
            onPriceEdited = viewModel::editPositionPrice,
            onPartEdited = viewModel::editPart,
            onRemovePart = viewModel::removePart,
            onMemberIncluded = viewModel::addPart,
            errorMessage = data.messages.map { it.getMessage() }.firstOrNull() ?: ""
        )

    private fun back() = findNavController().popBackStack()
}
