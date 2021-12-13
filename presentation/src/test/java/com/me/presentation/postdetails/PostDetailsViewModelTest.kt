package com.me.presentation.postdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.domain.usecases.CommentsUseCase
import com.me.domain.usecases.UserPostUseCase
import com.me.presentation.*
import com.me.presentation.model.Resource
import com.me.presentation.model.ResourceState
import com.me.presentation.model.mapToPresentation
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PostDetailsViewModelTest {

    private lateinit var viewModel: PostDetailsViewModel

    private val mockUserPostUseCase: UserPostUseCase = mock()
    private val mockCommentsUseCase: CommentsUseCase = mock()

    private val comments = listOf(commentEntity)

    private val userId = userEntity.id
    private val postId = postEntity.id

    private val throwable = Throwable()

//    // TODO : what is the benefit of this RxSchedulersOverrideRule ??
//    @Rule
//    @JvmField
//    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    // TODO : what is the benefit of this InstantTaskExecutorRule ??
    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = PostDetailsViewModel(mockUserPostUseCase, mockCommentsUseCase)
    }

    @Test
    fun `get post succeeds`() {

        // given
        whenever(mockUserPostUseCase.getPost(userId, postId, false))
            .thenReturn(Flowable.just(combinedUserPost))

        // when
        viewModel.getPost(UserIdPostId(userId, postId))

        // then
        verify(mockUserPostUseCase).getPost(userId, postId, false)
        Assert.assertEquals(combinedUserPost.mapToPresentation(), viewModel.post.value)
    }

    @Test
    fun `get comments succeeds`() {
        // given
        whenever(mockCommentsUseCase.getComments(postId, false)).thenReturn(Flowable.just(comments))

        // when
        viewModel.getComments(postId, false)

        // then
        verify(mockCommentsUseCase).getComments(postId, false)
        Assert.assertEquals(
            Resource(
                state = ResourceState.SUCCESS,
                data = comments.mapToPresentation(),
                message = null
            ), viewModel.comments.value
        )
    }

    @Test
    fun `get comments fails`() {
        // given
        whenever(mockCommentsUseCase.getComments(postId, false)).thenReturn(Flowable.error(throwable))

        // when
        viewModel.getComments(postId, false)

        // then
        verify(mockCommentsUseCase).getComments(postId, false)
        Assert.assertEquals(
            Resource(
                state = ResourceState.ERROR,
                data = null,
                message = throwable.message
            ), viewModel.comments.value
        )
    }
}