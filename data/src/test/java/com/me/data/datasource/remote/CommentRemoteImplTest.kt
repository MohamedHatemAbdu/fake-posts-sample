package com.me.data.datasource.remote

import com.me.data.api.CommentsApi
import com.me.data.commentData
import com.me.data.datasource.CommentRemoteImpl
import com.me.data.entities.mapToDomain
import com.me.data.postData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class CommentRemoteImplTest {

    // TDOD : What is lateinit
    private lateinit var dataSource: CommentRemoteImpl

    private val mockApi: CommentsApi = mock()

    private val postId = postData.id

    private val remoteItem = commentData.copy( name= "remote")

    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()


    @Before
    fun setUp() {
        dataSource = CommentRemoteImpl(mockApi)
    }

    @Test
    fun `get posts remote success`() {
        // given
        whenever(mockApi.getComments(postId)).thenReturn(Flowable.just(remoteList))

        // TODO : what is the test() at flowable
        // when
        val test = dataSource.getComments(postId).test()

        // then
        verify(mockApi).getComments(postId)
        test.assertValue(remoteList.mapToDomain())

    }

    @Test
    fun `get posts remote fail`() {
        // given
        whenever(mockApi.getComments(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getComments(postId).test()

        // then
        verify(mockApi).getComments(postId)
        test.assertError(throwable)
    }


}