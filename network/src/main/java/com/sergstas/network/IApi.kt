package com.sergstas.network

import com.sergstas.domain.models.EventData
import com.sergstas.network.models.RegistrationResponse
import com.sergstas.domain.models.PaymentData
import com.sergstas.domain.models.SessionData
import com.sergstas.domain.models.UserData
import com.sergstas.domain.models.auth.RegistrationParams
import retrofit2.Call
import retrofit2.http.*

interface IApi {
    @GET("api/Calculate")
    suspend fun calculateCheque(
        @Body sessionData: SessionData,
    ): List<PaymentData>

    @POST("api/User")
    fun register(
        @Body registrationData: RegistrationParams,
    ): RegistrationResponse

    @GET("api/User/{username}")
    fun login(
        @Path("username") username: String,
    ): Call<UserData>

    @GET("api/User")
    fun getAllUsers(): List<UserData>

    @GET("api/Session")
    fun getHistory(
        @Query("username") username: String,
    ): List<EventData>
}