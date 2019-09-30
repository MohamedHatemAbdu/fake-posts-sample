package com.rakshitjain.data.repository

import android.util.Log
import com.me.data.api.UsersApi
import com.me.data.entities.mapToDomain
import com.me.data.datasource.UserRemoteDataSource

import com.me.domain.entities.UserEntity
import io.reactivex.Flowable


class UserRemoteImpl constructor(private val api: UsersApi) : UserRemoteDataSource {

    val LOG_TAG = "UserRemoteImpl"

    override fun getUsers(): Flowable<List<UserEntity>> =
         api.getUsers().map {
            Log.d(LOG_TAG, "set users remote ${it.size}")
            it.mapToDomain()
        }


    override fun getUser(userId: String): Flowable<UserEntity> =
         api.getUser(userId).map { it.mapToDomain() }

}