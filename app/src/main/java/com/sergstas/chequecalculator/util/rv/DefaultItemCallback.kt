package com.sergstas.chequecalculator.util.rv

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.sergstas.chequecalculator.util.rv.models.AbstractItem

class DefaultItemCallback<T: AbstractItem>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem
}