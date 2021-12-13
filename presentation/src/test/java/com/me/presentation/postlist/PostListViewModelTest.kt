package com.me.presentation.postlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.domain.usecases.UsersPostsUseCase
import com.me.presentation.RxSchedulersOverrideRule
import com.me.presentation.combinedUserPost
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

class PostListViewModelTest {

    private lateinit var viewModel: PostListViewModel

    private val mockUseCase: UsersPostsUseCase = mock()

    private val combinedUserPostList = listOf(combinedUserPost)
    private val throwable = Throwable()

    // TODO : what is the benefit of this RxSchedulersOverrideRule ??
    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    // TODO : what is the benefit of this InstantTaskExecutorRule ??
    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = PostListViewModel(mockUseCase)
    }

    @Test
    fun `get post item list succeeds`() {
        // given
        whenever(mockUseCase.getPosts(false))
            .thenReturn(Flowable.just(combinedUserPostList))

        // when
        viewModel.get(false)

        // then

        verify(mockUseCase).getPosts(false)
        Assert.assertEquals(
            Resource(ResourceState.SUCCESS, combinedUserPostList.mapToPresentation(), null),
            viewModel.posts.value
        )
    }

    @Test
    fun `get post item list fails`() {
        // given
        whenever(mockUseCase.getPosts(false))
            .thenReturn(Flowable.error(throwable))

        // whe
        viewModel.get(false)

        // then
        verify(mockUseCase).getPosts(false)
        Assert.assertEquals(
            Resource(ResourceState.ERROR, null, throwable.message),
            viewModel.posts.value
        )
    }

}