package ru.vladroid.projs.mems.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.vladroid.projs.mems.network.request.AuthRequest
import ru.vladroid.projs.mems.network.response.AuthResponse
import ru.vladroid.projs.mems.network.response.MemResponse


interface MemesApi {

    @POST("auth/login")
    fun login(@Body authRequest: AuthRequest): Single<AuthResponse>

    @GET("memes")
    fun getMemes(): Single<List<MemResponse>>
}