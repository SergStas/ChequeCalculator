package com.sergstas.chequecalculator.util.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergstas.chequecalculator.App
import com.sergstas.chequecalculator.util.rv.models.AbstractItem

abstract class AbstractAdapter<T: AbstractItem, VH: AbstractViewHolder<T>>(
    private val viewHolderFactory: (parent: ViewGroup) -> VH,
): ListAdapter<T, VH>(DefaultItemCallback()) {
    companion object {
        fun<T: AbstractItem, VH: AbstractViewHolder<T>> create(factory: (ViewGroup) -> VH) =
            object: AbstractAdapter<T, VH>(factory) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        viewHolderFactory(parent)

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))

    fun bindToRecycler(rv: RecyclerView, isHorizontal: Boolean = false) {
        rv.adapter = this
        rv.layoutManager = LinearLayoutManager(
            App.instance,
            if (isHorizontal) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL,
            false,
        )
        (rv.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
    }
}