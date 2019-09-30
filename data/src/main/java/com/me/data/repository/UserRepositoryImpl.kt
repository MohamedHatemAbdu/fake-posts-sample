package com.me.domain.repositories

import android.util.Log
import com.me.data.datasource.UserCacheDataSource
import com.me.data.datasource.UserRemoteDataSource
import com.me.domain.entities.UserEntity
import io.reactivex.Flowable


class UserRepositoryImpl(
    val userCacheDataSource: UserCacheDataSource,
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    val LOG_TAG = "UserRepositoryImpl"

    override fun getUsers(refresh: Boolean): Flowable<List<UserEntity>> =

        when (refresh) {
            true -> {
                userRemoteDataSource.getUsers().flatMap {
                    Log.d(LOG_TAG, "set remote $it")
                    userCacheDataSource.setUsers(it)
                    userCacheDataSource.getUsers()
                }
            }
            false -> userCacheDataSource.getUsers().onErrorResumeNext(getUsers(true))
        }


    override fun getUser(userId: String, refresh: Boolean): Flowable<UserEntity> =
        when (refresh) {
            true -> {
                userRemoteDataSource.getUser(userId).flatMap {
                    userCacheDataSource.setUser(it)
                    userCacheDataSource.getUser(userId)

                }
            }
            false -> userCacheDataSource.getUser(userId).onErrorResumeNext(getUser(userId, true))
        }

}