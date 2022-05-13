package com.sergstas.domain.models

data class SessionData(
    val name: String,
    val date: Long,
    val participants: List<UserData>,
    val receipts: List<ReceiptData>,
) {
    data class ReceiptData(
        val name: String,
        val payer: UserData,
        val positions: List<PositionData>,
    )

    data class PositionData(
        val name: String,
        val price: Double,
        val parts: List<PartData>,
    )

    data class PartData(
        val user: UserData,
        val part: Double,
    )
}



