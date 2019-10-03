package com.me.data.datasource.cache


import com.me.data.datasource.UserCacheDataSource
import com.me.data.db.AppDatabase
import com.me.data.db.UserDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.domain.entities.UserEntity
import io.reactivex.Flowable

class UserCacheImpl(private val database: AppDatabase) : UserCacheDataSource {


    private val dao: UserDao = database.getUsersDao()

    override fun getUsers(): Flowable<List<UserEntity>> =
        dao.getAllUsers().map {
            if (it.isEmpty()) {
                throw Exception("Empty List")
            }
            it.mapToDomain()
        }


    override fun setUsers(usersList: List<UserEntity>): Flowable<List<UserEntity>> {
        dao.clear()
        dao.saveAllUsers(usersList.mapToData())
        return getUsers()
    }

    override fun getUser(userId: String): Flowable<UserEntity> =
        dao.getUser(userId).map {
            it.mapToDomain()
        }


    override fun setUser(post: UserEntity): Flowable<UserEntity> {
        dao.saveUser(post.mapToData())
        return getUser(post.id)
    }


}