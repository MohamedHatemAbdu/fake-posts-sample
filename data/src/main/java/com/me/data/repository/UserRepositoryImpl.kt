package com.me.domain.repositories

import com.me.data.datasource.UserCacheDataSource
import com.me.data.datasource.UserRemoteDataSource
import com.me.domain.entities.UserEntity
import io.reactivex.Flowable


class UserRepositoryImpl(
    val userCacheDataSource: UserCacheDataSource,
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getUsers(refresh: Boolean): Flowable<List<UserEntity>> {

        return when (refresh) {
            true -> userRemoteDataSource.getUsers().flatMap { userCacheDataSource.setUsers(it) }
            false -> userCacheDataSource.getUsers().onErrorResumeNext(getUsers(refresh))
        }
    }

    override fun getUser(userId: String, refresh: Boolean): Flowable<UserEntity> {
        return when (refresh) {
            true -> userRemoteDataSource.getUser(userId).flatMap { userCacheDataSource.setUser(it) }
            false -> userCacheDataSource.getUser(userId).onErrorResumeNext(getUser(userId, refresh))
        }
    }
}