package com.me.data.datasource.remote

import com.me.data.api.UsersApi
import com.me.data.entities.mapToDomain
import com.me.data.userData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rakshitjain.data.repository.UserRemoteImpl
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class UserRemoteImplTest {

    // TDOD : What is lateinit
    private lateinit var dataSource: UserRemoteImpl

    private val mockApi: UsersApi = mock()

    private val userId = userData.id

    private val remoteItem = userData.copy(name = "remote")

    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        dataSource = UserRemoteImpl(mockApi)
    }

    @Test
    fun `get posts remote success`() {
        // given
        whenever(mockApi.getUsers()).thenReturn(Flowable.just(remoteList))

        // TODO : what is the test() at flowable
        // when
        val test = dataSource.getUsers().test()

        // then
        verify(mockApi).getUsers()
        test.assertValue(remoteList.mapToDomain())
    }

    @Test
    fun `get posts remote fail`() {
        // given
        whenever(mockApi.getUsers()).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getUsers().test()

        // then
        verify(mockApi).getUsers()
        test.assertError(throwable)
    }

    @Test
    fun `get post remote success`() {

        // given
        whenever(mockApi.getUser(userId)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = dataSource.getUser(userId).test()

        // then
        verify(mockApi).getUser(userId)

        test.assertValue(remoteItem.mapToDomain())
    }

    @Test
    fun `get post remote fail`() {

        // given
        whenever(mockApi.getUser(userId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getUser(userId).test()

        // then
        verify(mockApi).getUser(userId)
        test.assertError(throwable)
    }
}