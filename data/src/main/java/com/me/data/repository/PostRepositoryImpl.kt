package com.me.domain.repositories

import com.me.data.datasource.PostCacheDataSource
import com.me.data.datasource.PostRemoteDataSource
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable


class PostRepositoryImpl
    (
    val postCacheDataSource: PostCacheDataSource,
    val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {

    override fun getPosts(refresh: Boolean): Flowable<List<PostEntity>> {
       return when (refresh) {

            true -> postRemoteDataSource.getPosts().flatMap { postCacheDataSource.setPosts(it) }
            false -> postCacheDataSource.getPosts().onErrorResumeNext(getPosts(refresh))
        }

    }


    override fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity> {
      return  when (refresh) {

            true -> postRemoteDataSource.getPost(postId).flatMap { postCacheDataSource.setPost(it) }
            false -> postCacheDataSource.getPost(postId).onErrorResumeNext(getPost(postId, true))
        }
    }

}

