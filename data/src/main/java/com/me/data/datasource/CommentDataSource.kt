package com.me.data.datasource

import com.me.domain.entities.CommentEntity
import io.reactivex.Flowable

interface CommentCacheDataSource {
    fun getComments(postId: String): Flowable<List<CommentEntity>>

    fun setComments(postId: String, commentsList: List<CommentEntity>): Flowable<List<CommentEntity>>
}

interface CommentRemoteDataSource {
    fun getComments(postId: String): Flowable<List<CommentEntity>>
}