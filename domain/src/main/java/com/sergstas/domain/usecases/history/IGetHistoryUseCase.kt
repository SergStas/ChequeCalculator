package com.sergstas.domain.usecases.history

import com.sergstas.domain.models.EventData

interface IGetHistoryUseCase {
    suspend operator fun invoke(): List<EventData>
}