package com.me.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.me.data.entities.UserData
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("Select * from user")
    fun getAllUsers(): Flowable<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllUsers(users: List<UserData>)

    @Query("Select * from user where id like :userId")
    fun getUser(userId: String): Flowable<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserData)

    @Query("DELETE FROM user")
    fun clear()
}