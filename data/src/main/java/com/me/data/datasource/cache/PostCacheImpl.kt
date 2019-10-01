package com.me.data.datasource.cache


import android.util.Log
import com.me.data.datasource.PostCacheDataSource
import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

class PostCacheImpl(private val database: AppDatabase) : PostCacheDataSource {

    val LOG_TAG = "PostCacheImpl"

    private val dao: PostDao = database.getPostsDao()

    override fun getPosts(): Flowable<List<PostEntity>> {
        return dao.getAllPosts().map {

            if (it.isEmpty()) {
                throw Exception("Empty List")
            }
            it.mapToDomain()
        }
    }

    override fun setPosts(postsList: List<PostEntity>) {
        dao.clear()
        Log.d(LOG_TAG, "save remote into db $postsList")
        dao.saveAllPosts(postsList.mapToData())
    }

    override fun getPost(postId: String): Flowable<PostEntity> {
        return dao.getPost(postId).map {
            it.mapToDomain()
        }
    }

    override fun setPost(post: PostEntity) {
        dao.savePost(post.mapToData())
    }


}