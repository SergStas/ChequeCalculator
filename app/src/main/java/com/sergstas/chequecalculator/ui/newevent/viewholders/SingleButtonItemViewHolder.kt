package com.sergstas.chequecalculator.ui.newevent.viewholders

import com.sergstas.chequecalculator.databinding.ItemSingleButtonTitleBinding
import com.sergstas.chequecalculator.ui.newevent.models.SingleButtonItem
import com.sergstas.chequecalculator.util.rv.AbstractViewHolder

class SingleButtonItemViewHolder(
    private val binding: ItemSingleButtonTitleBinding,
): AbstractViewHolder<SingleButtonItem>(binding.root) {
    override fun bind(data: SingleButtonItem) = with(binding) {
        isbTvTitle.text = data.title
        isbBAction.text = data.buttonText
        root.setOnClickListener { data.onTouch() }
        isbBAction.setOnClickListener { data.onButtonPressed() }
    }
}