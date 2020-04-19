package ru.vladroid.projs.mems.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.vladroid.projs.mems.network.request.AuthRequest
import ru.vladroid.projs.mems.network.response.AuthResponse


interface MemesApi {

    @POST("auth/login")
    fun login(@Body authRequest: AuthRequest): Single<AuthResponse>
}