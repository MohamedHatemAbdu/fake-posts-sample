package com.me.data.datasource.cache

import com.me.data.db.AppDatabase
import com.me.data.db.UserDao
import com.me.data.entities.mapToDomain
import com.me.data.userData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class UserCacheImplTest {

    private lateinit var dataSource: UserCacheImpl

    private var mockedDatabase: AppDatabase = mock()
    private var mockedUserDao: UserDao = mock()

    private val userId = userData.id

    private val remoteItem = userData.copy(name = "remote")
    private val cacheItem = userData.copy(name = "cached")


    private val remoteList = listOf(remoteItem)
    private val cacheList = listOf(cacheItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        whenever(mockedDatabase.getUsersDao()).thenReturn(mockedUserDao)
        dataSource = UserCacheImpl(mockedDatabase)
    }

    @Test
    fun `get users cache success`() {
        // given
        whenever(mockedUserDao.getAllUsers()).thenReturn(Flowable.just(cacheList))

        // when
        val test = dataSource.getUsers().test()

        // then
        verify(mockedUserDao).getAllUsers()
        test.assertValue(cacheList.mapToDomain())
    }

    @Test
    fun `get users cache fail`() {
        // given
        whenever(mockedUserDao.getAllUsers()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getUsers().test()

        // then
        verify(mockedUserDao).getAllUsers()
        test.assertError(throwable)
    }

    @Test
    fun `get user cache success`() {
        // given
        whenever(mockedUserDao.getUser(userId)).thenReturn(Flowable.just(cacheItem))

        // when
        val test = dataSource.getUser(userId).test()

        // then
        verify(mockedUserDao).getUser(userId)
        test.assertValue(cacheItem.mapToDomain())
    }

    @Test
    fun `get user cache fail`() {
        // given
        whenever(mockedUserDao.getUser(userId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getUser(userId).test()

        // then
        verify(mockedUserDao).getUser(userId)
        test.assertError(throwable)
    }


    @Test
    fun `set users cache success`() {
        // given
        whenever(mockedUserDao.getAllUsers()).thenReturn(Flowable.just(remoteList))

        // when
        val test = dataSource.setUsers(remoteList.mapToDomain()).test()

        // then
        verify(mockedUserDao).saveAllUsers(remoteList)
        test.assertValue(remoteList.mapToDomain())
    }

    @Test
    fun `set users cache fail`() {
        // given
        whenever(mockedUserDao.getAllUsers()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.setUsers(remoteList.mapToDomain()).test()

        // then
        verify(mockedUserDao).saveAllUsers(remoteList)
        test.assertError(throwable)
    }


    @Test
    fun `set user cache success`() {
        // given
        whenever(mockedUserDao.getUser(userId)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = dataSource.setUser(remoteItem.mapToDomain()).test()

        // then
        verify(mockedUserDao).saveUser(remoteItem)
        test.assertValue(remoteItem.mapToDomain())
    }

    @Test
    fun `set user cache fail`() {
        // given
        whenever(mockedUserDao.getUser(userId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.setUser(remoteItem.mapToDomain()).test()

        // then
        verify(mockedUserDao).saveUser(remoteItem)
        test.assertError(throwable)
    }
}