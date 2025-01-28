package com.example.mynews

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeConverter : JsonDeserializer<OffsetDateTime>, JsonSerializer<OffsetDateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): OffsetDateTime {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OffsetDateTime.parse(json?.asString, formatter)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    override fun serialize(
        src: OffsetDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            JsonPrimitive(formatter.format(src))
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}