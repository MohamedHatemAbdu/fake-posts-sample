package com.me.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.domain.entities.CommentEntity
import com.squareup.moshi.Json

@Entity(tableName = "comment")
data class CommentData(
    @field:Json(name = "PostID") val postId: String,
    @PrimaryKey @field:Json(name = "Id") val id: String,
    @field:Json(name = "Name") val name: String,
    @field:Json(name = "Email") val email: String,
    @field:Json(name = "Body") val body: String
)

fun CommentData.mapToDomain(): CommentEntity = CommentEntity(postId, id, name, email, body)
fun List<CommentData>.mapToDomain(): List<CommentEntity> = map { it.mapToDomain() }

fun CommentEntity.mapToData(): CommentData = CommentData(postId, id, name, email, body)
fun List<CommentEntity>.mapToData(): List<CommentData> = map { it.mapToData() }
