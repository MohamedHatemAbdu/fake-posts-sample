package com.me.data.repository

import com.me.data.datasource.UserCacheDataSource
import com.me.data.datasource.UserRemoteDataSource
import com.me.data.userEntity
import com.me.domain.repositories.UserRepositoryImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl

    private val mockCacheDataSource: UserCacheDataSource = mock()
    private val mockRemoteDataSource: UserRemoteDataSource = mock()

    private val userId = userEntity.id

    private val cacheItem = userEntity.copy(name = "cache")
    private val remoteItem = userEntity.copy(name = "remote")

    private val cacheList = listOf(cacheItem)
    private val remoteList = listOf(remoteItem)

    private val cacheThrowable = Throwable()
    private val remoteThrowable = Throwable()

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(mockCacheDataSource, mockRemoteDataSource)
    }

    @Test
    fun `get users cache success`() {
        // given
        whenever(mockCacheDataSource.getUsers()).thenReturn(Flowable.just(cacheList))

        // when
        val test = repository.getUsers(false).test()

        // then
        verify(mockCacheDataSource).getUsers()
        test.assertValue(cacheList)
    }

    @Test
    fun `get users cache fail fallback remote succeeds`() {

        // given
        whenever(mockCacheDataSource.getUsers()).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getUsers()).thenReturn(Flowable.just(remoteList))
        whenever(mockCacheDataSource.setUsers(remoteList)).thenReturn(Flowable.just(remoteList))

        // when
        val test = repository.getUsers(false).test()

        // then
        verify(mockCacheDataSource).getUsers()
        verify(mockRemoteDataSource).getUsers()
        verify(mockCacheDataSource).setUsers(remoteList)
        test.assertValue(remoteList)
    }

    @Test
    fun `get users cache fail fallback remote fails`() {

        // given
        whenever(mockCacheDataSource.getUsers()).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getUsers()).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getUsers(false).test()

        // then
        verify(mockCacheDataSource).getUsers()
        verify(mockRemoteDataSource).getUsers()
        test.assertError(remoteThrowable)
    }

    @Test
    fun `get users remote success`() {
        // given
        whenever(mockRemoteDataSource.getUsers()).thenReturn(Flowable.just(remoteList))
        whenever(mockCacheDataSource.setUsers(remoteList)).thenReturn(Flowable.just(remoteList))

        // when
        val test = repository.getUsers(true).test()

        // then
        verify(mockRemoteDataSource).getUsers()
        verify(mockCacheDataSource).setUsers(remoteList)
        test.assertValue(remoteList)
    }

    @Test
    fun `get users remote fail`() {
        // given
        whenever(mockRemoteDataSource.getUsers()).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getUsers(true).test()

        // then
        verify(mockRemoteDataSource).getUsers()
        test.assertError(remoteThrowable)
    }

    @Test
    fun `get user cache success`() {
        // given
        whenever(mockCacheDataSource.getUser(userId)).thenReturn(Flowable.just(cacheItem))

        // when
        val test = repository.getUser(userId, false).test()

        // then
        verify(mockCacheDataSource).getUser(userId)
        test.assertValue(cacheItem)
    }

    @Test
    fun `get user cache fail fallback remote succeeds`() {

        // given
        whenever(mockCacheDataSource.getUser(userId)).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getUser(userId)).thenReturn(Flowable.just(remoteItem))
        whenever(mockCacheDataSource.setUser(remoteItem)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = repository.getUser(userId, false).test()

        // then
        verify(mockCacheDataSource).getUser(userId)
        verify(mockRemoteDataSource).getUser(userId)
        verify(mockCacheDataSource).setUser(remoteItem)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get user cache fail fallback remote fails`() {

        // given
        whenever(mockCacheDataSource.getUser(userId)).thenReturn(Flowable.error(cacheThrowable))
        whenever(mockRemoteDataSource.getUser(userId)).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getUser(userId, false).test()

        // then
        verify(mockCacheDataSource).getUser(userId)
        verify(mockRemoteDataSource).getUser(userId)
        test.assertError(remoteThrowable)
    }

    @Test
    fun `get user remote success`() {
        // given
        whenever(mockRemoteDataSource.getUser(userId)).thenReturn(Flowable.just(remoteItem))
        whenever(mockCacheDataSource.setUser(remoteItem)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = repository.getUser(userId, true).test()

        // then
        verify(mockRemoteDataSource).getUser(userId)
        verify(mockCacheDataSource).setUser(remoteItem)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get user remote fail`() {
        // given
        whenever(mockRemoteDataSource.getUser(userId)).thenReturn(Flowable.error(remoteThrowable))

        // when
        val test = repository.getUser(userId, true).test()

        // then
        verify(mockRemoteDataSource).getUser(userId)
        test.assertError(remoteThrowable)
    }
}