package com.me.data.datasource.cache

import com.me.data.commentData
import com.me.data.db.AppDatabase
import com.me.data.db.CommentDao
import com.me.data.entities.mapToDomain
import com.me.data.postData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class CommentCacheImplTest {

    private lateinit var dataSource: CommentCacheImpl

    private var mockedDatabase: AppDatabase = mock()
    private var mockedCommentDao: CommentDao = mock()

    private val postId = postData.id

    private val remoteItem = commentData.copy(name = "remote")
    private val cacheItem = commentData.copy(name = "cached")

    private val remoteList = listOf(remoteItem)
    private val cacheList = listOf(cacheItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        whenever(mockedDatabase.getCommentsDao()).thenReturn(mockedCommentDao)
        dataSource = CommentCacheImpl(mockedDatabase)
    }

    @Test
    fun `get comments cache success`() {
        // given
        whenever(mockedCommentDao.getAllComments(postId)).thenReturn(Flowable.just(cacheList))

        // when
        val test = dataSource.getComments(postId).test()

        // then
        verify(mockedCommentDao).getAllComments(postId)
        test.assertValue(cacheList.mapToDomain())
    }

    @Test
    fun `get comments cache fail`() {
        // given
        whenever(mockedCommentDao.getAllComments(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getComments(postId).test()

        // then
        verify(mockedCommentDao).getAllComments(postId)
        test.assertError(throwable)
    }

    @Test
    fun `set comments cache success`() {
        // given
        whenever(mockedCommentDao.getAllComments(postId)).thenReturn(Flowable.just(remoteList))

        // when
        val test = dataSource.setComments(postId, remoteList.mapToDomain()).test()

        // then
        verify(mockedCommentDao).saveAllComments(remoteList)
        test.assertValue(remoteList.mapToDomain())
    }

    @Test
    fun `set comments cache fail`() {
        // given
        whenever(mockedCommentDao.getAllComments(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.setComments(postId, remoteList.mapToDomain()).test()

        // then
        verify(mockedCommentDao).saveAllComments(remoteList)
        test.assertError(throwable)
    }
}