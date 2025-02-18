package com.example.mynews

import android.os.Build
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.OffsetDateTime

class OffsetDateTimeConverter : JsonDeserializer<OffsetDateTime>, JsonSerializer<OffsetDateTime> {


    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OffsetDateTime {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OffsetDateTime.parse(json?.asString)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    override fun serialize(
        src: OffsetDateTime?,
        typeOfT: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
