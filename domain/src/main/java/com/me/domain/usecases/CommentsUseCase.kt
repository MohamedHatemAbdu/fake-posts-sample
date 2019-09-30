package com.me.domain.usecases;

import com.me.domain.entities.CommentEntity
import com.me.domain.entities.PostEntity
import com.me.domain.entities.UserEntity
import com.me.domain.repositories.CommentRepository
import com.me.domain.repositories.PostRepository
import com.me.domain.repositories.UserRepository
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction


class CommentsUseCase constructor(
    private val commentRepository: CommentRepository
) {
    fun getComments(postId: String, refresh: Boolean): Flowable<List<CommentEntity>> =
        commentRepository.getComments(postId, refresh)

}



