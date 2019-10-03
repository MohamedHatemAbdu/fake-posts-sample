package com.me.data.repository

import com.me.data.commentEntity
import com.me.data.datasource.CommentCacheDataSource
import com.me.data.datasource.CommentRemoteDataSource
import com.me.data.postEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class CommentRepositoryImplTest {

    private lateinit var repository: CommentRepositoryImpl

    private val mockCacheDataSource: CommentCacheDataSource = mock()
    private val mockRemoteDataSource: CommentRemoteDataSource = mock()

    private val postId = postEntity.id

    private val cacheItem = commentEntity.copy(name = "cache")
    private val remoteItem = commentEntity.copy(name = "remote")

    private val cacheList = listOf(cacheItem)
    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = CommentRepositoryImpl(mockCacheDataSource, mockRemoteDataSource)
    }

    @Test
    fun `get comments cache success`() {
        // given
        whenever(mockCacheDataSource.getComments(postId)).thenReturn(Flowable.just(cacheList))

        // when
        val test = repository.getComments(postId, false).test()

        // then
        verify(mockCacheDataSource).getComments(postId)
        test.assertValue(cacheList)
    }

    @Test
    fun `get comments cache fail fallback remote succeeds`() {
        // given
        whenever(mockCacheDataSource.getComments(postId)).thenReturn(Flowable.error(throwable))
        whenever(mockRemoteDataSource.getComments(postId)).thenReturn(Flowable.just(remoteList))
        whenever(mockCacheDataSource.setComments(postId, remoteList)).thenReturn(Flowable.just(remoteList))

        // when
        val test = repository.getComments(postId, false).test()

        // then
        verify(mockCacheDataSource).getComments(postId)
        verify(mockRemoteDataSource).getComments(postId)
        verify(mockCacheDataSource).setComments(postId, remoteList)
        test.assertValue(remoteList)
    }

    @Test
    fun `get comments remote success`() {
        // given
        whenever(mockRemoteDataSource.getComments(postId)).thenReturn(Flowable.just(remoteList))
        whenever(mockCacheDataSource.setComments(postId, remoteList)).thenReturn(Flowable.just(remoteList))

        // when
        val test = repository.getComments(postId, true).test()

        // then
        verify(mockRemoteDataSource).getComments(postId)
        verify(mockCacheDataSource).setComments(postId, remoteList)
        test.assertValue(remoteList)
    }

    @Test
    fun `get comments remote fail`() {
        // given
        whenever(mockRemoteDataSource.getComments(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = repository.getComments(postId, true).test()

        // then
        verify(mockRemoteDataSource).getComments(postId)
        test.assertError(throwable)
    }
}