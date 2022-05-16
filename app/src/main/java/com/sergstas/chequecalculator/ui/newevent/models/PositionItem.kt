package com.sergstas.chequecalculator.ui.newevent.models

import com.sergstas.chequecalculator.util.rv.models.AbstractItem
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import com.sergstas.domain.models.UserData

data class PositionItem(
    val id: Int,
    val name: String,
    val price: Double,
    val parts: List<PartItem>,
    val members: List<UserData>,
    val isExpanded: Boolean,
    val onExpand: () -> Unit,
    val onRemove: () -> Unit,
    val onTitleEdited: (String) -> Unit,
    val onPriceEdited: (Double) -> Unit,
    val onMemberIncluded: (String) -> Unit,
    val errorMessage: String = "",
): AbstractItem() {
    companion object {
        fun fromPositionData(
            idGenerator: () -> Int,
            data: NewEventViewModel.PositionIndexed,
            isExpanded: Boolean,
            members: List<UserData>,
            onExpand: (Int) -> Unit,
            onRemove: (Int) -> Unit,
            onTitleEdited: (Int, String) -> Unit,
            onPriceEdited: (Int, Double) -> Unit,
            onRemovePart: (Int, Int) -> Unit,
            onPartEdited: (Int, Int, Double) -> Unit,
            onMemberIncluded: (Int, String) -> Unit,
            errorMessage: String = "",
        ): PositionItem = PositionItem(
            id = data.id,
            name = data.name,
            price = data.price,
            parts = data.parts.map { PartItem.fromPartData(
                id = idGenerator(),
                data = it.toPartData(),
                onRemove = { partId -> onRemovePart(data.id, partId) },
                onPartEdited = { partId, value -> onPartEdited(data.id, partId, value) },
            ) },
            members = members,
            isExpanded = isExpanded,
            onExpand = { onExpand(data.id) },
            onRemove = { onRemove(data.id) },
            onTitleEdited = { name -> onTitleEdited(data.id, name) },
            onPriceEdited = { price -> onPriceEdited(data.id, price) },
            onMemberIncluded = { name -> onMemberIncluded(data.id, name) },
            errorMessage = errorMessage,
        )
    }
}