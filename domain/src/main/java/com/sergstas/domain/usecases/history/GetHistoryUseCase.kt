package com.sergstas.domain.usecases.history

import com.sergstas.domain.models.EventData
import com.sergstas.domain.repository.IEventsRepository
import com.sergstas.domain.repository.IUserRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val eventsRepository: IEventsRepository,
    private val userRepository: IUserRepository,
): IGetHistoryUseCase {
    override suspend fun invoke(): List<EventData> {
        val user = userRepository.getLoggedInUser() ?: return emptyList()
        return eventsRepository.getEventsHistory(user)
    }
}