package com.me.data.api

import com.me.data.entities.PostData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("posts/")
    fun getPosts() : Flowable<List<PostData>>

    @GET("posts/{id}")
    fun getPost(@Path("id") postId: String) : Flowable<PostData>
}

//interface UsersApi {
//
//    @GET("users/")
//    fun getUsers()
//
//    @GET("users/{id}")
//    fun getUser()
//}
//
//interface CommentsApi {
//
//    @GET("comments")
//    fun getComments(@Query("postId") postId: String)
//
//}
