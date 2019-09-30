package com.me.data.datasource

import android.util.Log
import com.me.data.api.PostsApi
import com.me.data.entities.mapToDomain
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable


class PostRemoteImpl constructor(private val api: PostsApi) : PostRemoteDataSource {

    val LOG_TAG = "PostRemoteImpl"

    override fun getPosts(): Flowable<List<PostEntity>> =
        api.getPosts().map {
            Log.d(LOG_TAG, "set posts remote ${it.size}")
            it.mapToDomain()
        }


    override fun getPost(postId: String): Flowable<PostEntity> =
        api.getPost(postId).map { it.mapToDomain() }

}