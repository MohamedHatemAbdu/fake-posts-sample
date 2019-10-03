package com.me.data.datasource

import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

interface PostCacheDataSource {

    fun getPosts(): Flowable<List<PostEntity>>

    fun setPosts(postsList: List<PostEntity>): Flowable<List<PostEntity>>

    fun getPost(postId: String): Flowable<PostEntity>

    fun setPost(post: PostEntity): Flowable<PostEntity>

}

interface PostRemoteDataSource {

    fun getPosts(): Flowable<List<PostEntity>>

    fun getPost(postId: String): Flowable<PostEntity>

}