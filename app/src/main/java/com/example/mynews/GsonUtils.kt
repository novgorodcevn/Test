package com.example.mynews

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.OffsetDateTime

object GsonUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeConverter())
        .create()
}