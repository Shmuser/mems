package ru.vladroid.projs.mems.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface MemesApi {

    @POST("auth/login")
    fun login(@Body authInfo: AuthInfo): Single<AuthResponse>

    @GET("memes")
    fun getMemes(): Single<ArrayList<Mem>>
}