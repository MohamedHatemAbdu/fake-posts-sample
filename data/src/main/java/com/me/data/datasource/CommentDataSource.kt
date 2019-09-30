package com.me.data.datasource

import com.me.domain.entities.CommentEntity
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

interface CommentCacheDataSource {
    fun getComments(postId: String): Flowable<List<CommentEntity>>

    fun setComments(postId: String, commentsList: List<CommentEntity>)
}

interface CommentRemoteDataSource {
    fun getComments(postId: String): Flowable<List<CommentEntity>>
}