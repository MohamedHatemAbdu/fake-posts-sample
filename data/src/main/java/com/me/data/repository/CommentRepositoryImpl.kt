package com.me.data.repository

import com.me.data.datasource.CommentCacheDataSource
import com.me.data.datasource.CommentRemoteDataSource
import com.me.domain.entities.CommentEntity
import com.me.domain.repositories.CommentRepository
import io.reactivex.Flowable

class CommentRepositoryImpl(
    val commentCacheDataSource: CommentCacheDataSource,
    val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    override fun getComments(postId: String, refresh: Boolean): Flowable<List<CommentEntity>> =
        when (refresh) {

            true -> {
                commentRemoteDataSource.getComments(postId).flatMap {
                    commentCacheDataSource.setComments(postId, it)
                }
            }
            false -> commentCacheDataSource.getComments(postId)
                .onErrorResumeNext { t: Throwable -> getComments(postId, true) }
        }
}