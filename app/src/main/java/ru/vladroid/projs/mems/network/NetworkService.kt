package ru.vladroid.projs.mems.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {
    companion object {
        private const val baseUrl = "http://demo2407529.mockable.io/"
        private val memesApiInstance: MemesApi = getRetrofit().create(MemesApi::class.java)

        private fun getRetrofit(): Retrofit {
            Log.d("wut", "agaga")
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .build()
        }

        private fun getOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

            return OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(4, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
        }

        fun getApiInstance() = memesApiInstance
    }
}