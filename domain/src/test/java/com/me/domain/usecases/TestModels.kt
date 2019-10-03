package com.me.domain.usecases


import com.me.domain.entities.CommentEntity
import com.me.domain.entities.PostEntity
import com.me.domain.entities.UserEntity


val userEntity = UserEntity("userId", "name", "username", "email")
val postEntity = PostEntity("userId", "postId", "title", "body")
val commentEntity = CommentEntity("postId", "commentId", "name", "email", "body")
