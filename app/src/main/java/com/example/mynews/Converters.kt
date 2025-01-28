package com.example.mynews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.OffsetDateTime

class Converters {

    @TypeConverter
    fun fromOffsetDateTime(offsetDateTime: OffsetDateTime): String {
        return offsetDateTime.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toOffsetDateTime(offsetDateTime: String): OffsetDateTime {
        return OffsetDateTime.parse(offsetDateTime)
    }
}