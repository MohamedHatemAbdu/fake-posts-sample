package com.me.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.me.data.entities.PostData
import io.reactivex.Flowable

@Dao
interface PostsDao {

    @Query("Select * from posts")
    fun getAllPosts(): Flowable<List<PostData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPosts(articles: List<PostData>)

    @Query("DELETE FROM posts")
    fun clear()

}