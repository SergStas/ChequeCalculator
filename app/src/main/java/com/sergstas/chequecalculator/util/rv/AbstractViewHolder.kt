package com.sergstas.chequecalculator.util.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sergstas.chequecalculator.util.rv.models.AbstractItem

abstract class AbstractViewHolder<T: AbstractItem>(
    view: View,
): RecyclerView.ViewHolder(view) {
    abstract fun bind(data: T)
}