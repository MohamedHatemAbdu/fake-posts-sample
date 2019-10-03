package com.me.domain.usecases

import com.me.domain.repositories.CommentRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class CommentsUseCaseTest {

    private lateinit var usecase: CommentsUseCase
    private val mockRepository: CommentRepository = mock()
    private val postId = postEntity.id
    private val commentList = listOf(commentEntity)

    val throwable = Throwable()

    @Before
    fun setUp() {
        usecase = CommentsUseCase(mockRepository)
    }

    @Test
    fun `repository get success`() {
        // given
        whenever(mockRepository.getComments(postId, false)).thenReturn(Flowable.just(commentList))


        // when
        val test = usecase.getComments(postId, false).test()


        // then
        verify(mockRepository).getComments(postId, false)

        // test
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertComplete()
        test.assertValue(commentList)
    }

    @Test
    fun `repository get fail`() {
        // given
        whenever(mockRepository.getComments(postId, false)).thenReturn(Flowable.error(throwable))


        // when
        val test = usecase.getComments(postId, false).test()


        // then
        verify(mockRepository).getComments(postId, false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)

    }
}