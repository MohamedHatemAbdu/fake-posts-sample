package com.me.domain.repositories

import android.util.Log
import com.me.data.datasource.PostCacheDataSource
import com.me.data.datasource.PostRemoteDataSource
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


class PostRepositoryImpl
    (
    val postCacheDataSource: PostCacheDataSource,
    val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {

    val LOG_TAG = "PostRepositoryImpl"

    override fun getPosts(refresh: Boolean): Flowable<List<PostEntity>> =
        when (refresh) {

            true -> {
                postRemoteDataSource.getPosts().flatMap {
                    Log.d(LOG_TAG, "set remote $it")
                    postCacheDataSource.setPosts(it)
                    postCacheDataSource.getPosts()
                }

            }
            false -> postCacheDataSource.getPosts().onErrorResumeNext(getPosts(true))


        }


    override fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity> =
        when (refresh) {

            true -> {
                postRemoteDataSource.getPost(postId).flatMap {
                    postCacheDataSource.setPost(it)
                    postCacheDataSource.getPost(postId)

                }
            }
            false -> postCacheDataSource.getPost(postId).onErrorResumeNext(getPost(postId, true))
        }


}

