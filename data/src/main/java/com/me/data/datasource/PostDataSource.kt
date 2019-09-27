package com.me.data.datasource

import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

interface PostCacheDataSource {

    fun getPosts(): Flowable<List<PostEntity>>

    fun setPosts(postsList: List<PostEntity>)

    fun getPost(postId: String): Flowable<PostEntity>

    fun setPost(post: PostEntity)

}

interface PostRemoteDataSource {

    fun getPosts(): Flowable<List<PostEntity>>

    fun getPost(postId: String): Flowable<PostEntity>

}