package com.evgeniasokolova.chefsadvices.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.evgeniasokolova.chefsadvices.data.api.Owner
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@ProvidedTypeConverter
@Singleton
class OwnerConverter @Inject constructor(private val moshi: Moshi) {

    @TypeConverter
    fun fromOwner(data: Owner): String {
        return moshi.adapter(Owner::class.java).toJson(data)
    }

    @TypeConverter
    fun toOwner(json: String): Owner? {
        return moshi.adapter(Owner::class.java).fromJson(json)
    }
}