package com.evgeniasokolova.chefsadvices.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.evgeniasokolova.chefsadvices.data.db.OwnerConverter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionList(
    val items: List<Question>
)

@JsonClass(generateAdapter = true)
@Entity(tableName = "questions")
@TypeConverters(OwnerConverter::class)
data class Question(
    @PrimaryKey
    val question_id: String,
    val title: String,
    var body: String,
    var isFavorite: Boolean = false,
    var view_count: Int = 0,
    var answer_count: Int? = 0,
    var score: Int?= 0,
    val owner: Owner,
)

@TypeConverters(OwnerConverter::class)
@JsonClass(generateAdapter = true)
data class Owner(
    val profile_image: String?,
    val display_name: String
)