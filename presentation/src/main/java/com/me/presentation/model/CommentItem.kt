package com.me.presentation.model

import com.me.domain.entities.CommentEntity

data class CommentItem(
    val postId: String,
    val id: String,
    val name: String,
    val email: String,
    val body: String
)

fun List<CommentEntity>.mapToPresentation(): List<CommentItem> =
    map { CommentItem(it.postId, it.id, it.name, it.email, it.body) }