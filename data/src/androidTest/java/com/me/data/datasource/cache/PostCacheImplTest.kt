package com.me.data.datasource.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.me.data.db.AppDatabase
import com.me.data.entities.mapToDomain
import com.me.data.postData
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostCacheImplTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dataSource: PostCacheImpl

    private val postId = postData.id

    private val remoteItem = postData.copy(title = "remote")
    private val cacheItem = postData.copy(title = "cached")


    private val remoteList = listOf(remoteItem)
    private val cacheList = listOf(cacheItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context,
            appDatabase::class.java
        ).build()

        dataSource = PostCacheImpl(appDatabase)
    }

    @Test
    fun getpostscachesuccess() {
        // given
//        whenever(appDatabase.getPostsDao().getAllPosts()).thenReturn(Flowable.just(cacheList))

        // when
        val test = dataSource.getPosts().test()

        // then
//        verify(appDatabase).getPostsDao().getAllPosts()
        test.assertValue(cacheList.mapToDomain())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }


}