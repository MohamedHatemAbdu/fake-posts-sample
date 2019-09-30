package com.me.domain.repositories

import com.me.domain.entities.CommentEntity
import io.reactivex.Flowable


interface CommentRepository {
    fun getComments(postId: String, refresh: Boolean): Flowable<List<CommentEntity>>
}