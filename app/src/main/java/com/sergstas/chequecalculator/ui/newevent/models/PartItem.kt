package com.sergstas.chequecalculator.ui.newevent.models

import com.sergstas.chequecalculator.util.rv.models.AbstractItem
import com.sergstas.domain.models.SessionData

data class PartItem(
    val id: Int,
    val name: String,
    val part: Double,
    val onRemove: () -> Unit,
    val onPartEdited: (Double) -> Unit,
): AbstractItem() {
    companion object {
        fun fromPartData(
            id: Int,
            data: SessionData.PartData,
            onRemove: (Int) -> Unit,
            onPartEdited: (Int, Double) -> Unit,
        ) = PartItem(
            id = id,
            name = data.user.name,
            part = data.part,
            onRemove = { onRemove(id) },
            onPartEdited = { part -> onPartEdited(id, part) },
        )
    }
}