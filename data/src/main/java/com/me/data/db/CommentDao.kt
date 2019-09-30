package com.me.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.me.data.entities.CommentData
import com.me.data.entities.UserData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CommentDao {

    @Query("Select * from comment where postId like :postId")
    fun getAllComments(postId: String): Flowable<List<CommentData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllComments(comments: List<CommentData>)

    @Query("DELETE FROM comment")
    fun clear()

}