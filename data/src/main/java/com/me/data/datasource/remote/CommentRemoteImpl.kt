package com.me.data.datasource

import com.me.data.api.CommentsApi
import com.me.data.entities.mapToDomain
import com.me.domain.entities.CommentEntity
import io.reactivex.Flowable

class CommentRemoteImpl constructor(private val api: CommentsApi) : CommentRemoteDataSource {

    override fun getComments(postId: String): Flowable<List<CommentEntity>> =
        api.getComments(postId).map {
            it.mapToDomain()
        }
}