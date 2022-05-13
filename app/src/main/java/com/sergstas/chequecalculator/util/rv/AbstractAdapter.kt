package com.sergstas.chequecalculator.util.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sergstas.chequecalculator.util.rv.models.AbstractItem

abstract class AbstractAdapter<T: AbstractItem, VH: AbstractViewHolder<T>>(
    private val viewHolderFactory: (parent: ViewGroup) -> VH,
): ListAdapter<T, VH>(DefaultItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        viewHolderFactory(parent)

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))
}