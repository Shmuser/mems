package ru.vladroid.projs.mems.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkApiService {
    companion object {
        private const val baseUrl = "http://demo2407529.mockable.io/"
        private var memesApiInstance: MemesApi? = null

        private fun getRetrofit(): Retrofit {
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

        fun getApiInstance(): MemesApi {
            if (memesApiInstance == null) {
                memesApiInstance = getRetrofit().create(MemesApi::class.java)
            }
            return memesApiInstance!!
        }
    }
}