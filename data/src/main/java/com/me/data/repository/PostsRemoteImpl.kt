package com.rakshitjain.data.repository

import com.me.data.api.PostsApi
import com.me.data.entities.mapToDomain
import com.me.data.repository.PostsDataSource
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

class PostsRemoteImpl constructor(private val api: PostsApi) : PostsDataSource {

    override fun getPosts(): Flowable<List<PostEntity>> {

        return api.getPosts().map { it.mapToDomain() }
    }

}