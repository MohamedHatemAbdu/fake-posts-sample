package com.me.data.datasource.cache

import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.data.entities.mapToDomain
import com.me.data.postData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class PostCacheImplTest {

    private lateinit var dataSource: PostCacheImpl

    private var mockedDatabase: AppDatabase = mock()
    private var mockedPostDao: PostDao = mock()

    private val postId = postData.id

    private val remoteItem = postData.copy(title = "remote")
    private val cacheItem = postData.copy(title = "cached")


    private val remoteList = listOf(remoteItem)
    private val cacheList = listOf(cacheItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        whenever(mockedDatabase.getPostsDao()).thenReturn(mockedPostDao)
        dataSource = PostCacheImpl(mockedDatabase)
    }

    @Test
    fun `get posts cache success`() {
        // given
        whenever(mockedPostDao.getAllPosts()).thenReturn(Flowable.just(cacheList))

        // when
        val test = dataSource.getPosts().test()

        // then
        verify(mockedPostDao).getAllPosts()
        test.assertValue(cacheList.mapToDomain())
    }

    @Test
    fun `get posts cache fail`() {
        // given
        whenever(mockedPostDao.getAllPosts()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getPosts().test()

        // then
        verify(mockedPostDao).getAllPosts()
        test.assertError(throwable)
    }

    @Test
    fun `get post cache success`() {
        // given
        whenever(mockedPostDao.getPost(postId)).thenReturn(Flowable.just(cacheItem))

        // when
        val test = dataSource.getPost(postId).test()

        // then
        verify(mockedPostDao).getPost(postId)
        test.assertValue(cacheItem.mapToDomain())
    }

    @Test
    fun `get post cache fail`() {
        // given
        whenever(mockedPostDao.getPost(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getPost(postId).test()

        // then
        verify(mockedPostDao).getPost(postId)
        test.assertError(throwable)
    }


    @Test
    fun `set posts cache success`() {
        // given
        whenever(mockedPostDao.getAllPosts()).thenReturn(Flowable.just(remoteList))

        // when
        val test = dataSource.setPosts(remoteList.mapToDomain()).test()

        // then
        verify(mockedPostDao).saveAllPosts(remoteList)
        test.assertValue(remoteList.mapToDomain())
    }

    @Test
    fun `set posts cache fail`() {
        // given
        whenever(mockedPostDao.getAllPosts()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.setPosts(remoteList.mapToDomain()).test()

        // then
        verify(mockedPostDao).saveAllPosts(remoteList)
        test.assertError(throwable)
    }


    @Test
    fun `set post cache success`() {
        // given
        whenever(mockedPostDao.getPost(postId)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = dataSource.setPost(remoteItem.mapToDomain()).test()

        // then
        verify(mockedPostDao).savePost(remoteItem)
        test.assertValue(remoteItem.mapToDomain())
    }

    @Test
    fun `set post cache fail`() {
        // given
        whenever(mockedPostDao.getPost(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.setPost(remoteItem.mapToDomain()).test()

        // then
        verify(mockedPostDao).savePost(remoteItem)
        test.assertError(throwable)
    }


}