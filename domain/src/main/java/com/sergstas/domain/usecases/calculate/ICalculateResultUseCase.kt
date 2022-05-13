package com.sergstas.domain.usecases.calculate

import com.example.domain.models.calculation.CalculationParams
import com.example.domain.models.calculation.CalculationResult

interface ICalculateResultUseCase {
    suspend operator fun invoke(params: CalculationParams): CalculationResult
}