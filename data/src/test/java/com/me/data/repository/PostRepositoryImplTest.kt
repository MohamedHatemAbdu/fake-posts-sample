package com.me.data.repository

import com.me.data.datasource.PostCacheDataSource
import com.me.data.datasource.PostRemoteDataSource
import com.me.data.postEntity
import com.me.domain.repositories.PostRepositoryImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class PostRepositoryImplTest {

    private lateinit var repository: PostRepositoryImpl

    private val mockCacheDataSource: PostCacheDataSource = mock()
    private val mockRemoteDataSource: PostRemoteDataSource = mock()

    private val postId = postEntity.id

    private val cacheItem = postEntity.copy(title = "cache")
    private val remoteItem = postEntity.copy(title = "remote")

    private val cacheList = listOf(cacheItem)
    private val remoteList = listOf(remoteItem)

    private val cacheThrowable = Throwable()
    private val remoteThrowable = Throwable()

    @Before
    fun setUp() {
        repository = PostRepositoryImpl(mockCacheDataSource, mockRemoteDataSource)
    }

    @Test
    fun `get posts cache success`() {
        // given
        whenever(mockCacheDataSource.getPosts()).thenReturn(Flowable.just(cacheList))

        // when
        val test = repository.getPosts(false).test()

        // then
        verify(mockCacheDataSource).getPosts()
        test.assertError(cacheThrowable)
    }

    @Test
    fun `get posts cache fail fallback remote succeeds`() {

        // given
        whenever(mockCacheDataSource.getPosts()).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getPosts()).thenReturn(Flowable.just(remoteList))
        whenever(mockCacheDataSource.setPosts(remoteList)).thenReturn(Flowable.just(remoteList))

        // when
        val test = repository.getPosts(false).test()

        // then
        verify(mockCacheDataSource).getPosts()
        verify(mockRemoteDataSource).getPosts()
        verify(mockCacheDataSource).setPosts(remoteList)
        test.assertValue(remoteList)
    }

    @Test
    fun `get posts cache fail fallback remote fails`() {

        // given
        whenever(mockCacheDataSource.getPosts()).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getPosts()).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getPosts(false).test()

        // then
        verify(mockCacheDataSource).getPosts()
        verify(mockRemoteDataSource).getPosts()
        test.assertError(remoteThrowable)
    }

    @Test
    fun `get posts remote success`() {
        // given
        whenever(mockRemoteDataSource.getPosts()).thenReturn(Flowable.just(remoteList))
        whenever(mockCacheDataSource.setPosts(remoteList)).thenReturn(Flowable.just(remoteList))

        // when
        val test = repository.getPosts(true).test()

        // then
        verify(mockRemoteDataSource).getPosts()
        verify(mockCacheDataSource).setPosts(remoteList)
        test.assertValue(remoteList)
    }

    @Test
    fun `get posts remote fail`() {
        // given
        whenever(mockRemoteDataSource.getPosts()).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getPosts(true).test()

        // then
        verify(mockRemoteDataSource).getPosts()
        test.assertError(remoteThrowable)
    }

    @Test
    fun `get post cache success`() {
        // given
        whenever(mockCacheDataSource.getPost(postId)).thenReturn(Flowable.just(cacheItem))

        // when
        val test = repository.getPost(postId, false).test()

        // then
        verify(mockCacheDataSource).getPost(postId)
        test.assertValue(cacheItem)
    }

    @Test
    fun `get post cache fail fallback remote succeeds`() {

        // given
        whenever(mockCacheDataSource.getPost(postId)).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getPost(postId)).thenReturn(Flowable.just(remoteItem))
        whenever(mockCacheDataSource.setPost(remoteItem)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = repository.getPost(postId, false).test()

        // then
        verify(mockCacheDataSource).getPost(postId)
        verify(mockRemoteDataSource).getPost(postId)
        verify(mockCacheDataSource).setPost(remoteItem)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get post cache fail fallback remote fails`() {

        // given
        whenever(mockCacheDataSource.getPost(postId)).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getPost(postId)).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getPost(postId, false).test()

        // then
        verify(mockCacheDataSource).getPost(postId)
        verify(mockRemoteDataSource).getPost(postId)
        test.assertError(remoteThrowable)
    }

    @Test
    fun `get post remote success`() {
        // given
        whenever(mockRemoteDataSource.getPost(postId)).thenReturn(Flowable.just(remoteItem))
        whenever(mockCacheDataSource.setPost(remoteItem)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = repository.getPost(postId, true).test()

        // then
        verify(mockRemoteDataSource).getPost(postId)
        verify(mockCacheDataSource).setPost(remoteItem)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get post remote fail`() {
        // given
        whenever(mockRemoteDataSource.getPost(postId)).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getPost(postId, true).test()

        // then
        verify(mockRemoteDataSource).getPost(postId)
        test.assertError(remoteThrowable)
    }
}