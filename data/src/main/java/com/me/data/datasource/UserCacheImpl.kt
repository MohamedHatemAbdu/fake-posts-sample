package com.me.data.datasource


import com.me.data.db.AppDatabase
import com.me.data.db.UserDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.domain.entities.UserEntity
import io.reactivex.Flowable

class UserCacheImpl(private val database: AppDatabase) : UserCacheDataSource {

    private val dao: UserDao = database.getUsersDao()

    override fun getUsers(): Flowable<List<UserEntity>> {
        return dao.getAllUsers().map {
            it.mapToDomain()
        }
    }

    override fun setUsers(usersList: List<UserEntity>): Flowable<List<UserEntity>> {
        dao.clear()
        return dao.saveAllUsers(usersList.mapToData()).map { it.mapToDomain() }
    }

    override fun getUser(userId: String): Flowable<UserEntity> {
        return dao.getUser(userId).map {
            it.mapToDomain()
        }
    }

    override fun setUser(post: UserEntity): Flowable<UserEntity> {
        return dao.saveUser(post.mapToData()).map { it.mapToDomain() }
    }


}