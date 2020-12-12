package com.example.katia.examenandroidbranchbitapp.request

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitSingleton {

    private var retrofit: Retrofit
    val BASE_URL = "https://dl.dropboxusercontent.com/s/5u21281sca8gj94/"
    var okHttpClient = OkHttpClient()
    private const val TIMEOUT_REQUEST: Long = 15

    init {
        okHttpClient = initOkHttp()!!.build()
        retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(initOkHttp()!!.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        println("Singleton has been instantiated")
    }

    fun getInstance(): Retrofit = retrofit

    fun initOkHttp(): OkHttpClient.Builder? {
        var builder = OkHttpClient.Builder()
        var interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
        builder.readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
        builder.writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
        builder.addInterceptor(interceptor)
        return builder
    }

}

