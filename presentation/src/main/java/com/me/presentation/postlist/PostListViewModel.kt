package com.me.presentation.postlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.domain.usecases.UsersPostsUseCase
import com.me.presentation.extenstions.setError
import com.me.presentation.extenstions.setLoading
import com.me.presentation.extenstions.setSuccess
import com.me.presentation.model.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostListViewModel constructor(private val usersPostsUseCase: UsersPostsUseCase) : ViewModel() {

    val posts = MutableLiveData<Resource<List<PostItem>>>()
    private val compositeDisposable = CompositeDisposable()


    fun get(refresh: Boolean = false) =
        compositeDisposable.add(
            usersPostsUseCase.getPosts(refresh)
            .doOnSubscribe {posts.setLoading()}
            .subscribeOn(Schedulers.io())
            .map { it.mapToPresentation() }
            .subscribe({
                posts.setSuccess(it)
            },{
                posts.setError(it.message)
            })
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}