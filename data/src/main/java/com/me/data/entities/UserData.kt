package com.me.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.domain.entities.PostEntity
import com.me.domain.entities.UserEntity
import com.squareup.moshi.Json

@Entity(tableName = "user")
data class UserData(
    @PrimaryKey @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "username") val username: String,
    @field:Json(name = "email") val email: String
)

fun UserData.mapToDomain(): UserEntity = UserEntity(id, name, username, email)
fun List<UserData>.mapToDomain(): List<UserEntity> = map { it.mapToDomain() }

fun UserEntity.mapToData(): UserData = UserData(id, name, username, email)
fun List<UserEntity>.mapToData(): List<UserData> = map { it.mapToData() }
