package com.me.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.data.entities.CommentData
import com.me.data.entities.PostData
import com.me.data.entities.UserData

@Database(entities = [UserData::class, PostData::class, CommentData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPostsDao(): PostDao
    abstract fun getUsersDao(): UserDao
    abstract fun getCommentsDao(): CommentDao

}