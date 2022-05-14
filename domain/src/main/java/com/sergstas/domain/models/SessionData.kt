package com.sergstas.domain.models

import java.io.Serializable

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
    ): Serializable {
        val price get() = positions.sumOf { it.price }
    }

    data class PositionData(
        val name: String,
        val price: Double,
        val parts: List<PartData>,
    )

    data class PartData(
        val user: UserData,
        val part: Double,
    ) {
        companion object {
            fun distributeBetween(users: List<UserData>) =
                users.map { userData ->
                    PartData(
                        user = userData,
                        part = 100.0 / users.size,
                    )
                }
        }
    }
}



