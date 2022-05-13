package com.sergstas.domain.models

data class PaymentData(
    val sender: UserData,
    val transactions: List<TransactionData>,
) {
    data class TransactionData(
        val receiver: UserData,
        val sum: Double,
    )
}