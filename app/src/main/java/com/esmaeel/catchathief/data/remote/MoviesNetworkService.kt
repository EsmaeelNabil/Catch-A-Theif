package com.esmaeel.moviesapp.data.remote

import com.esmaeel.catchathief.data.models.BotMessagesResponse
import com.esmaeel.catchathief.data.models.TelegramMessageRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkService {

    @GET("getUpdates")
    suspend fun getUserIDs(): Response<BotMessagesResponse>

    @POST("sendMessage")
    suspend fun sendTelegramUpdate(@Body messageModel: TelegramMessageRequest): Response<ResponseBody>

}