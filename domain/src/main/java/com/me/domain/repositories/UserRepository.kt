package com.me.domain.repositories

import com.me.domain.entities.UserEntity
import io.reactivex.Flowable


interface UserRepository {

    fun getUsers(refresh: Boolean): Flowable<List<UserEntity>>

    fun getUser(userId: String, refresh: Boolean): Flowable<UserEntity>

}