package com.evgeniasokolova.chefsadvices.data.db

import androidx.room.*
import com.evgeniasokolova.chefsadvices.data.api.Question


@Entity(tableName = "favorite_table")
data class FavoriteQuestion(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String
)