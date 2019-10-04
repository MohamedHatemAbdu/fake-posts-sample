package com.me.presentation


import com.me.domain.entities.CommentEntity
import com.me.domain.entities.PostEntity
import com.me.domain.entities.UserEntity
import com.me.domain.usecases.CombinedUserPost


val userEntity = UserEntity("userId", "name", "username", "email")
val postEntity = PostEntity("userId", "postId", "title", "body")
val commentEntity = CommentEntity("postId", "commentId", "name", "email", "body")

val combinedUserPost = CombinedUserPost(userEntity, postEntity)
