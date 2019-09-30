package com.me.data.datasource

import android.util.Log
import com.me.data.api.CommentsApi
import com.me.data.entities.mapToDomain
import com.me.domain.entities.CommentEntity
import io.reactivex.Flowable


class CommentRemoteImpl constructor(private val api: CommentsApi) : CommentRemoteDataSource {

    val LOG_TAG = "CommentRemoteImpl"

    override fun getComments(postId: String): Flowable<List<CommentEntity>> =
        api.getComments(postId).map {
            Log.d(LOG_TAG, "set posts remote ${it.size}")
            it.mapToDomain()
        }


}