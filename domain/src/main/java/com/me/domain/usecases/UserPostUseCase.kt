package com.me.domain.usecases;

import com.me.domain.entities.PostEntity
import com.me.domain.entities.UserEntity
import com.me.domain.repositories.PostRepository
import com.me.domain.repositories.UserRepository
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction


data class CombinedUserPost(val user: UserEntity, val post: PostEntity)

class UsersPostsUseCase constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun getPosts(refresh: Boolean): Flowable<List<CombinedUserPost>> =
        Flowable.zip(
            userRepository.getUsers(refresh), postRepository.getPosts(refresh),
            BiFunction {
                    usersList, postsList -> map(usersList, postsList)
            })

}

class UserPostUseCase constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun getPost(userId: String, postId: String, refresh: Boolean): Flowable<CombinedUserPost> =
        Flowable.zip(
            userRepository.getUser(userId, refresh), postRepository.getPost(postId, refresh),
            BiFunction { user, post -> map(user, post) }
        )

}

fun map(user: UserEntity, post: PostEntity): CombinedUserPost = CombinedUserPost(user, post)

fun map(userList: List<UserEntity>, postList: List<PostEntity>): List<CombinedUserPost> {
    return postList.map { post -> CombinedUserPost(userList.first { post.userId == it.id }, post) }
}
