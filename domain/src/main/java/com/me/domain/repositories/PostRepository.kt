package com.me.domain.repositories

import com.me.domain.entities.PostEntity
import io.reactivex.Flowable


interface PostRepository {

    fun getPosts(): Flowable<List<PostEntity>>
    fun getLocalPosts(): Flowable<List<PostEntity>>
    fun getRemotePosts(): Flowable<List<PostEntity>>

}