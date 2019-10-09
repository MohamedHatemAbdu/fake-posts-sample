package com.me.domain.repositories

import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

interface PostRepository {

    fun getPosts(refresh: Boolean): Flowable<List<PostEntity>>

    fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity>
}