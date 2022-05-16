package com.sergstas.chequecalculator.ui.newevent.viewholders

import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.databinding.ItemPositionBinding
import com.sergstas.chequecalculator.databinding.ItemPositionMemberBinding
import com.sergstas.chequecalculator.ui.newevent.models.PositionItem
import com.sergstas.chequecalculator.util.extensions.setOnNewValueListener
import com.sergstas.chequecalculator.util.rv.AbstractAdapter
import com.sergstas.chequecalculator.util.rv.AbstractViewHolder
import com.sergstas.chequecalculator.util.spinner.DefaultSpinnerAdapter

class PositionItemViewHolder(
    private val binding: ItemPositionBinding,
): AbstractViewHolder<PositionItem>(binding.root) {
    private val context get() = binding.root.context

    override fun bind(data: PositionItem) = with(binding) {
        ipTvName.text = data.name
        ipTvPrice.text =
            if (data.price >= 1e-2) context.getString(R.string.price_ph).format(data.price.toFloat())
            else ""
        ipBExpand.setOnClickListener { data.onExpand() }
        if (data.isExpanded) {
            ipLlExpanded.isVisible = true
            ipBExpand.text = context.getString(R.string.button_hide)
            setEditTextFields(data)
            setAddingPanel(data)
            setErrorMessage(data)
            ipBRemove.setOnClickListener { data.onRemove() }
            setList(data)
        } else {
            ipLlExpanded.isVisible = false
            ipBExpand.text = context.getString(R.string.button_expand)
        }
    }

    private fun setList(data: PositionItem) = with(binding) {
        val adapter = AbstractAdapter.create { PartItemViewHolder(
            ItemPositionMemberBinding.inflate(LayoutInflater.from(it.context), it, false),
        ) }
        adapter.bindToRecycler(ipRvMembers)
        adapter.submitList(data.parts)
    }

    private fun setErrorMessage(data: PositionItem) = with(binding) {
        ipTvError.text = data.errorMessage
        ipCardError.isVisible = data.errorMessage.isNotEmpty()
    }

    private fun setEditTextFields(data: PositionItem) = with(binding) {
        ipEtName.setOnNewValueListener(data.onTitleEdited)
        if (data.name != ipEtName.text.toString()) {
            ipEtName.setText(data.name)
        }
        val priceToken = "%.2f".format(data.price.toFloat())
        if (priceToken != ipEtPrice.text.toString()) {
            ipEtPrice.setText(priceToken)
        }
        ipEtPrice.setOnNewValueListener {
            val price = it.toDoubleOrNull() ?: return@setOnNewValueListener
            data.onPriceEdited(price)
        }
    }

    private fun setAddingPanel(data: PositionItem) = with(binding) {
        val availableMembers = data.members.map { it.name }.toMutableList().apply {
            removeAll(data.parts.map { it.name })
        }
        setSpinner(availableMembers)
        ipLlAdd.isVisible = availableMembers.isNotEmpty()
        ipBAdd.setOnClickListener {
            if (availableMembers.isEmpty()) return@setOnClickListener
            val user = neNrSpinnerMember.selectedItem as? String ?: return@setOnClickListener
            data.onMemberIncluded(user)
        }
    }

    private fun setSpinner(list: List<String>) {
        binding.neNrSpinnerMember.adapter = DefaultSpinnerAdapter(list)
    }
}
