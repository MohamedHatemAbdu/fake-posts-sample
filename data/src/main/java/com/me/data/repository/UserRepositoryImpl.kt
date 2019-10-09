package com.me.domain.repositories

import com.me.data.datasource.UserCacheDataSource
import com.me.data.datasource.UserRemoteDataSource
import com.me.domain.entities.UserEntity
import io.reactivex.Flowable

class UserRepositoryImpl(
    val userCacheDataSource: UserCacheDataSource,
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getUsers(refresh: Boolean): Flowable<List<UserEntity>> =

        when (refresh) {
            true -> {
                userRemoteDataSource.getUsers().flatMap {
                    userCacheDataSource.setUsers(it)
                }
            }
            false -> userCacheDataSource.getUsers()
                .onErrorResumeNext { t: Throwable -> getUsers(true) }
        }

    override fun getUser(userId: String, refresh: Boolean): Flowable<UserEntity> =
        when (refresh) {
            true -> {
                userRemoteDataSource.getUser(userId).flatMap {
                    userCacheDataSource.setUser(it)
                }
            }
            false -> userCacheDataSource.getUser(userId)
                .onErrorResumeNext { t: Throwable -> getUser(userId, true) }
        }
}