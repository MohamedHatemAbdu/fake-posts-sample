package com.me.data.datasource.cache

import com.me.data.datasource.CommentCacheDataSource
import com.me.data.db.AppDatabase
import com.me.data.db.CommentDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.domain.entities.CommentEntity
import io.reactivex.Flowable

class CommentCacheImpl(private val database: AppDatabase) : CommentCacheDataSource {

    private val dao: CommentDao = database.getCommentsDao()

    override fun getComments(postId: String): Flowable<List<CommentEntity>> =
        dao.getAllComments(postId).map {
            if (it.isEmpty()) {
                throw Exception("Empty List")
            }
            it.mapToDomain()
        }

    override fun setComments(postId: String, commentsList: List<CommentEntity>): Flowable<List<CommentEntity>> {
        dao.saveAllComments(commentsList.mapToData())
        return getComments(postId)
    }
}