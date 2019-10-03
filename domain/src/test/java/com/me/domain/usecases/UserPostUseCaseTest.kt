package com.me.domain.usecases

import com.me.domain.repositories.PostRepository
import com.me.domain.repositories.UserRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class UsersPostsUseCaseTest {

    private lateinit var usersPostsUseCase: UsersPostsUseCase
    private val mockUserRepository: UserRepository = mock()
    private val mockPostRepository: PostRepository = mock()


    private val usersList = listOf(userEntity)
    private val postsList = listOf(postEntity)

    val throwable = Throwable()

    @Before
    fun setUp() {
        usersPostsUseCase = UsersPostsUseCase(mockUserRepository, mockPostRepository)
    }


    @Test
    fun `repository get success`() {
        // given
        whenever(mockUserRepository.getUsers(false)).thenReturn(Flowable.just(usersList))
        whenever(mockPostRepository.getPosts(false)).thenReturn(Flowable.just(postsList))

        // when
        val test = usersPostsUseCase.getPosts(false).test()

        // test
        verify(mockUserRepository).getUsers(false)
        verify(mockPostRepository).getPosts(false)

        test.assertNoErrors()
        test.assertComplete()
        // TODO : what is assertValueCount?
        test.assertValueCount(1)
        test.assertValue(map(usersList, postsList))

    }

    @Test
    fun `repository get fail`() {
        // given
        whenever(mockUserRepository.getUsers(false)).thenReturn(Flowable.error(throwable))
        whenever(mockPostRepository.getPosts(false)).thenReturn(Flowable.error(throwable))

        // when
        val test = usersPostsUseCase.getPosts(false).test()

        //
        // then
        verify(mockUserRepository).getUsers(false)
        verify(mockPostRepository).getPosts(false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)

    }

}

class UserPostUseCaseTest {

    private lateinit var userPostUseCase: UserPostUseCase
    private val mockUserRepository: UserRepository = mock()
    private val mockPostRepository: PostRepository = mock()


    private val userId = userEntity.id
    private val postId = postEntity.id

    val throwable = Throwable()

    @Before
    fun setUp() {
        userPostUseCase = UserPostUseCase(mockUserRepository, mockPostRepository)
    }


    @Test
    fun `repository get success`() {
        // given
        whenever(mockUserRepository.getUser(userId, false)).thenReturn(Flowable.just(userEntity))
        whenever(mockPostRepository.getPost(postId, false)).thenReturn(Flowable.just(postEntity))

        // when
        val test = userPostUseCase.getPost(userId, postId, false).test()

        // test
        verify(mockUserRepository).getUser(userId, false)
        verify(mockPostRepository).getPost(postId, false)

        test.assertNoErrors()
        test.assertComplete()
        // TODO : what is assertValueCount?
        test.assertValueCount(1)
        test.assertValue(map(userEntity, postEntity))

    }

    @Test
    fun `repository get fail`() {
        // given
        whenever(mockUserRepository.getUser(userId, false)).thenReturn(Flowable.error(throwable))
        whenever(mockPostRepository.getPost(postId, false)).thenReturn(Flowable.error(throwable))

        // when
        val test = userPostUseCase.getPost(userId, postId, false).test()

        // test
        verify(mockUserRepository).getUser(userId, false)
        verify(mockPostRepository).getPost(postId, false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)

    }

}