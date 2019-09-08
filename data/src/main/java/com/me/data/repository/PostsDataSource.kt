package com.me.data.repository

import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

interface PostsDataSource {
    fun getPosts(): Flowable<List<PostEntity>>
}