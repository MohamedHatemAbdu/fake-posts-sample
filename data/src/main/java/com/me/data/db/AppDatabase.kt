package com.me.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.data.entities.PostData

@Database(entities = arrayOf(PostData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPostsDao(): PostsDao
}