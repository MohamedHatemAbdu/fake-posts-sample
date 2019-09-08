package com.rakshitjain.data.repository


import com.me.data.db.AppDatabase
import com.me.data.db.PostsDao
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import com.me.data.repository.PostsDataSource
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

class PostsCacheImpl(private val database: AppDatabase) : PostsDataSource {

    private val dao: PostsDao = database.getPostsDao()

    override fun getPosts(): Flowable<List<PostEntity>> {
        return dao.getAllPosts().map {
            it.mapToDomain()
        }
    }

    fun saveArticles(it: List<PostEntity>) {
        dao.clear()
        dao.saveAllPosts(it.mapToData())
    }

}