package com.example.mynews

import android.annotation.SuppressLint
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://newsapi.org/v2/"

    fun buildApiService(@SuppressLint("NewApi") gson: Gson = GsonUtils.gson): NewsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(NewsApiService::class.java)
    }
}