package com.me.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.domain.entities.PostEntity
import com.squareup.moshi.Json

@Entity(tableName = "post")
data class PostData(
    @field:Json(name = "UserId") val userId: String,
    @PrimaryKey @field:Json(name = "Id") val id: String,
    @field:Json(name = "Title") val title: String,
    @field:Json(name = "Body") val body: String
)

fun PostData.mapToDomain(): PostEntity = PostEntity(userId, id, title, body)
fun List<PostData>.mapToDomain(): List<PostEntity> = map { it.mapToDomain() }

fun PostEntity.mapToData(): PostData = PostData(userId, id, title, body)
fun List<PostEntity>.mapToData(): List<PostData> = map { it.mapToData() }
