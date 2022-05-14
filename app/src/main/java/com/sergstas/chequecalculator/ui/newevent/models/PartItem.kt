package com.sergstas.chequecalculator.ui.newevent.models

import com.sergstas.chequecalculator.util.rv.models.AbstractItem
import com.sergstas.domain.models.SessionData

data class PartItem(
    val name: String,
    val part: Double,
    val onRemove: () -> Unit,
    val onPartEdited: (Double) -> Unit,
): AbstractItem() {
    companion object {
        fun fromPartData(
            data: SessionData.PartData,
            onRemove: (SessionData.PartData) -> Unit,
            onPartEdited: (SessionData.PartData, Double) -> Unit,
        ) = PartItem(
            name = data.user.name,
            part = data.part,
            onRemove = { onRemove(data) },
            onPartEdited = { part -> onPartEdited(data, part) },
        )
    }
}