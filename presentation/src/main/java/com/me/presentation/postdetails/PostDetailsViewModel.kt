package com.me.presentation.postdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.domain.usecases.CommentsUseCase
import com.me.domain.usecases.UserPostUseCase
import com.me.presentation.extenstions.setError
import com.me.presentation.extenstions.setLoading
import com.me.presentation.extenstions.setSuccess
import com.me.presentation.model.CommentItem
import com.me.presentation.model.PostItem
import com.me.presentation.model.Resource
import com.me.presentation.model.mapToPresentation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

data class UserIdPostId(val userId: String, val postId: String)

class PostDetailsViewModel(
    val userPostUseCase: UserPostUseCase,
    val commentsUseCase: CommentsUseCase
) : ViewModel() {

    val post = MutableLiveData<PostItem>()
    val comments = MutableLiveData<Resource<List<CommentItem>>>()
    private val compositeDisposable = CompositeDisposable()

    fun getPost(ids: UserIdPostId) {
        compositeDisposable.add(
            userPostUseCase.getPost(
                ids.userId, ids.postId, false
            ).subscribeOn(Schedulers.io()).map { it.mapToPresentation() }
                .subscribe({ post.postValue(it) }, { })
        )
    }

    fun getComments(postId: String, refresh: Boolean = false) {
        compositeDisposable.add(commentsUseCase.getComments(postId, refresh)
            .doOnSubscribe { comments.setLoading() }
            .subscribeOn(Schedulers.io())
            .map { it.mapToPresentation() }
            .subscribe({ comments.setSuccess(it) }, { comments.setError(it.message) }))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}