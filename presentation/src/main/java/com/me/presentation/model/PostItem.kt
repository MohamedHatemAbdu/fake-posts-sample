package com.me.presentation.model

import com.me.domain.usecases.CombinedUserPost

data class PostItem(
    val postId: String,
    val userId: String,
    val title: String,
    val body: String,
    val name: String,
    val username: String,
    val email: String
)

fun CombinedUserPost.mapToPresentation(): PostItem = PostItem(
    post.id,
    user.id,
    post.title,
    post.body,
    user.name,
    user.username,
    user.email
)

fun List<CombinedUserPost>.mapToPresentation(): List<PostItem> = map { it.mapToPresentation() }