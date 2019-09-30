package com.me.presentation.di

import androidx.room.Room
import com.me.data.api.CommentsApi
import com.me.data.api.PostsApi
import com.me.data.api.UsersApi
import com.me.data.datasource.*
import com.me.data.db.AppDatabase
import com.me.data.repository.CommentRepositoryImpl
import com.me.domain.repositories.*
import com.me.domain.usecases.CommentsUseCase
import com.me.domain.usecases.UserPostUseCase
import com.me.domain.usecases.UsersPostsUseCase
import com.me.presentation.BuildConfig
import com.me.presentation.postdetails.PostDetailsViewModel
import com.me.presentation.postlist.PostListViewModel
import com.rakshitjain.data.repository.UserRemoteImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit


// TODO : why using this function
fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule,
        localModules
    )
}

val viewModelModule: Module = module {
    viewModel { PostListViewModel(usersPostsUseCase = get()) }
    viewModel { PostDetailsViewModel(commentsUseCase = get(), userPostUseCase = get()) }

}

val useCaseModule: Module = module {
    factory { UsersPostsUseCase(userRepository = get(), postRepository = get()) }
    factory { UserPostUseCase(userRepository = get(), postRepository = get()) }
    factory { CommentsUseCase(commentRepository = get()) }

}

val repositoryModule: Module = module {
    single { UserRepositoryImpl(userCacheDataSource = get(), userRemoteDataSource = get()) as UserRepository }
    single { PostRepositoryImpl(postCacheDataSource = get(), postRemoteDataSource = get()) as PostRepository }
    single {
        CommentRepositoryImpl(
            commentCacheDataSource = get(),
            commentRemoteDataSource = get()
        ) as CommentRepository
    }

}

val dataSourceModule: Module = module {
    single { UserCacheImpl(database = get(DATABASE)) as UserCacheDataSource }
    single { UserRemoteImpl(api = usersApi) as UserRemoteDataSource }
    single { PostCacheImpl(database = get(DATABASE)) as PostCacheDataSource }
    single { PostRemoteImpl(api = postsApi) as PostRemoteDataSource }
    single { CommentCacheImpl(database = get(DATABASE)) as CommentCacheDataSource }
    single { CommentRemoteImpl(api = commentsApi) as CommentRemoteDataSource }
}

val networkModule: Module = module {
    single { usersApi }
    single { postsApi }
    single { commentsApi }

}

val localModules = module {
    single(name = DATABASE) {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "fake_posts").build()
    }
}
// TODO : why const ??
private const val BASE_URL = "https://my-json-server.typicode.com/MohamedHatem/fake-news-jsonplaceholder/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)

private val postsApi: PostsApi = retrofit.create(PostsApi::class.java)
private val usersApi: UsersApi = retrofit.create(UsersApi::class.java)
private val commentsApi: CommentsApi = retrofit.create(CommentsApi::class.java)

private const val DATABASE = "database"