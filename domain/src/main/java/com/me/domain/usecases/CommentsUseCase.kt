package com.me.domain.usecases

import com.me.domain.entities.CommentEntity
import com.me.domain.repositories.CommentRepository
import io.reactivex.Flowable

class CommentsUseCase constructor(
    private val commentRepository: CommentRepository
) {
    fun getComments(postId: String, refresh: Boolean): Flowable<List<CommentEntity>> =
        commentRepository.getComments(postId, refresh)
}
