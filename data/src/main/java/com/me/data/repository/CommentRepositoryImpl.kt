package com.me.data.repository

import android.util.Log
import com.me.data.datasource.CommentCacheDataSource
import com.me.data.datasource.CommentRemoteDataSource
import com.me.domain.entities.CommentEntity
import com.me.domain.repositories.CommentRepository
import io.reactivex.Flowable

class CommentRepositoryImpl(
    val commentCacheDataSource: CommentCacheDataSource,
    val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    val LOG_TAG = "CommentRepositoryImpl"

    override fun getComments(postId: String, refresh: Boolean): Flowable<List<CommentEntity>> =
        when (refresh) {

            true -> {
                commentRemoteDataSource.getComments(postId).flatMap {
                    Log.d(LOG_TAG, "set remote $it")
                    commentCacheDataSource.setComments(postId, it)
                    commentCacheDataSource.getComments(postId)
                }

            }
            false -> commentCacheDataSource.getComments(postId).onErrorResumeNext(getComments(postId, true))
        }


}