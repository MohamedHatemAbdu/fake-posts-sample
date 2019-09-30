package com.me.data.api

import com.me.data.entities.CommentData
import com.me.data.entities.PostData
import com.me.data.entities.UserData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApi {

    @GET("posts/")
    fun getPosts(): Flowable<List<PostData>>

    @GET("posts/{id}")
    fun getPost(@Path("id") postId: String): Flowable<PostData>
}

interface UsersApi {

    @GET("users/")
    fun getUsers(): Flowable<List<UserData>>

    @GET("users/{id}")
    fun getUser(@Path("id") userId: String): Flowable<UserData>
}

interface CommentsApi {

    @GET("comments")
    fun getComments(@Query("postId") postId: String): Flowable<List<CommentData>>

}
