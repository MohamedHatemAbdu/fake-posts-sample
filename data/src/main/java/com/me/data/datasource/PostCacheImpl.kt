package com.me.data.datasource


import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

class PostCacheImpl(private val database: AppDatabase) : PostCacheDataSource {

    private val dao: PostDao = database.getPostsDao()

    override fun getPosts(): Flowable<List<PostEntity>> {
        return dao.getAllPosts().map {
            it.mapToDomain()
        }
    }

    override fun setPosts(postsList: List<PostEntity>): Flowable<List<PostEntity>> {
        dao.clear()
        return dao.saveAllPosts(postsList.mapToData()).map { it.mapToDomain() }
    }

    override fun getPost(postId: String): Flowable<PostEntity> {
        return dao.getPost(postId).map {
            it.mapToDomain()
        }
    }

    override fun setPost(post: PostEntity): Flowable<PostEntity> {
        return dao.savePost(post.mapToData()).map { it.mapToDomain() }
    }


}