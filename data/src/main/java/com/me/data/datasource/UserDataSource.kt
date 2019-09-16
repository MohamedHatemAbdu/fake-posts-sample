package com.me.data.datasource

import com.me.domain.entities.UserEntity
import io.reactivex.Flowable

interface UserCacheDataSource {

    fun getUsers(): Flowable<List<UserEntity>>

    fun setUsers(usersList: List<UserEntity>): Flowable<List<UserEntity>>

    fun getUser(userId: String): Flowable<UserEntity>

    fun setUser(post: UserEntity): Flowable<UserEntity>
}

interface UserRemoteDataSource {

    fun getUsers(): Flowable<List<UserEntity>>

    fun getUser(userId: String): Flowable<UserEntity>
}