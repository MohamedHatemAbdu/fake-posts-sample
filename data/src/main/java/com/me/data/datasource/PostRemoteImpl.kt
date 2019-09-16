package com.me.data.datasource

import com.me.data.api.PostsApi
import com.me.data.entities.mapToDomain
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable


class PostRemoteImpl constructor(private val api: PostsApi) : PostRemoteDataSource {

    override fun getPosts(): Flowable<List<PostEntity>> {
        return api.getPosts().map { it.mapToDomain() }
    }

    override fun getPost(postId: String): Flowable<PostEntity> {
        return api.getPost(postId).map { it.mapToDomain() }
    }
}