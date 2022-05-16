package com.sergstas.chequecalculator.ui.newevent.viewholders

import com.sergstas.chequecalculator.databinding.ItemPositionMemberBinding
import com.sergstas.chequecalculator.ui.newevent.models.PartItem
import com.sergstas.chequecalculator.util.extensions.setOnNewValueListener
import com.sergstas.chequecalculator.util.rv.AbstractViewHolder

class PartItemViewHolder(
    private val binding: ItemPositionMemberBinding,
): AbstractViewHolder<PartItem>(binding.root) {
    override fun bind(data: PartItem) = with(binding) {
        ipmTvUsername.text = data.name
        ipmBRemove.setOnClickListener { data.onRemove() }
        ipmEtPart.setOnNewValueListener {
            data.onPartEdited(it.toDoubleOrNull() ?: return@setOnNewValueListener)
        }
        val partToken = "%.2f".format(data.part.toFloat())
        if (partToken != ipmEtPart.text.toString()) {
            ipmEtPart.setText(partToken)
        }
    }
}