package com.sergstas.domain.usecases.calculate

import com.example.domain.models.calculation.CalculationParams
import com.example.domain.models.calculation.CalculationResult
import com.sergstas.domain.repository.IEventsRepository
import javax.inject.Inject

class CalculateResultUseCase @Inject constructor(
    private val calculationRepository: IEventsRepository,
): ICalculateResultUseCase {
    override suspend fun invoke(params: CalculationParams): CalculationResult =
        calculationRepository.calculateChequesForEvent(params.sessionData)
}