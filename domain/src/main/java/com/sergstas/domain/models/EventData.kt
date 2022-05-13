package com.sergstas.domain.models

data class EventData(
    var sessionData: SessionData,
    var payments: List<PaymentData>,
)