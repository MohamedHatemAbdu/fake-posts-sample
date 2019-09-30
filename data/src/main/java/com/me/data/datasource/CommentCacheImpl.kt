package com.me.data.datasource


import android.util.Log
import com.me.data.db.AppDatabase
import com.me.data.db.CommentDao
import com.me.data.db.PostDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.domain.entities.CommentEntity
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

class CommentCacheImpl(private val database: AppDatabase) : CommentCacheDataSource {


    val LOG_TAG = "CommentCacheImpl"

    private val dao: CommentDao = database.getCommentsDao()


    override fun getComments(postId: String): Flowable<List<CommentEntity>> =
        dao.getAllComments(postId).map {
            if (it.isEmpty()) {
                throw Exception("Empty List")
            }
            it.mapToDomain()
        }


    override fun setComments(postId: String, commentsList: List<CommentEntity>) {
        Log.d(LOG_TAG, "save remote into db $commentsList")
        dao.saveAllComments(commentsList.mapToData())
    }


}