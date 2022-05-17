package com.sergstas.network

import com.sergstas.domain.models.EventData
import com.sergstas.domain.models.PaymentData
import com.sergstas.domain.models.SessionData
import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.RegistrationParams
import com.sergstas.network.models.RegistrationResponse
import retrofit2.Response
import retrofit2.http.*

interface IApi {
    @GET("api/Calculate")
    suspend fun calculateCheque(
        @Body sessionData: SessionData,
    ): List<PaymentData>

    @POST("api/User")
    suspend fun register(
        @Body registrationData: RegistrationParams,
    ): RegistrationResponse

    @GET("api/User/{username}")
    suspend fun login(
        @Path("username") username: String,
    ): Response<UserData>

    @GET("api/User")
    suspend fun getAllUsers(): List<UserData>

    @GET("api/Session")
    suspend fun getHistory(
        @Query("username") username: String,
    ): List<EventData>
}