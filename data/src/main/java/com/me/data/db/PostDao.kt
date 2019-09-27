package com.me.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.me.data.entities.PostData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PostDao {

    @Query("Select * from post")
    fun getAllPosts(): Flowable<List<PostData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPosts(articles: List<PostData>)

    @Query("Select * from post where id like :postId")
    fun getPost(postId: String): Flowable<PostData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePost(post: PostData)


    @Query("DELETE FROM post")
    fun clear()

}