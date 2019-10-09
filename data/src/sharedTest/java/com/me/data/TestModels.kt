package com.me.data

import com.me.data.entities.CommentData
import com.me.data.entities.PostData
import com.me.data.entities.UserData
import com.me.domain.entities.CommentEntity
import com.me.domain.entities.PostEntity
import com.me.domain.entities.UserEntity

val userData = UserData("userId", "name", "username", "email")
val postData = PostData("userId", "postId", "title", "body")
val commentData = CommentData("postId", "commentId", "name", "email", "body")

val userEntity = UserEntity("userId", "name", "username", "email")
val postEntity = PostEntity("userId", "postId", "title", "body")
val commentEntity = CommentEntity("postId", "commentId", "name", "email", "body")
