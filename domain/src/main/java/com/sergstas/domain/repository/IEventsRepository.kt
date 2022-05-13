package com.sergstas.domain.repository

import com.sergstas.domain.models.EventData
import com.sergstas.domain.models.SessionData
import com.sergstas.domain.models.UserData
import com.example.domain.models.calculation.CalculationResult

interface IEventsRepository {
    suspend fun calculateChequesForEvent(sessionData: SessionData): CalculationResult

    suspend fun getEventsHistory(user: UserData): List<EventData>
}