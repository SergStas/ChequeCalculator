package com.sergstas.chequecalculator.ui.newevent.models

import com.sergstas.chequecalculator.util.rv.models.AbstractItem
import com.sergstas.domain.models.SessionData.PartData
import com.sergstas.domain.models.SessionData.PositionData
import com.sergstas.domain.models.UserData

data class PositionItem(
    val name: String,
    val price: Double,
    val parts: List<PartItem>,
    val members: List<UserData>,
    val onRemove: () -> Unit,
    val onTitleEdited: (String) -> Unit,
    val onPriceEdited: (Double) -> Unit,
    val onMemberIncluded: (String) -> Unit,
    val errorMessage: String = "",
): AbstractItem() {
    companion object {
        fun fromPositionData(
            data: PositionData,
            members: List<UserData>,
            onRemove: (PositionData) -> Unit,
            onTitleEdited: (PositionData, String) -> Unit,
            onPriceEdited: (PositionData, Double) -> Unit,
            onRemovePart: (PositionData, PartData) -> Unit,
            onPartEdited: (PositionData, PartData, Double) -> Unit,
            onMemberIncluded: (PositionData, String) -> Unit,
            errorMessage: String = "",
        ) = PositionItem(
            name = data.name,
            price = data.price,
            parts = data.parts.map { PartItem.fromPartData(
                data = it,
                onRemove = { part -> onRemovePart(data, part) },
                onPartEdited = { part, value -> onPartEdited(data, part, value) },
            ) },
            members = members,
            onRemove = { onRemove(data) },
            onTitleEdited = { name -> onTitleEdited(data, name) },
            onPriceEdited = { price -> onPriceEdited(data, price) },
            onMemberIncluded = { name -> onMemberIncluded(data, name) },
            errorMessage = errorMessage,
        )
    }
}