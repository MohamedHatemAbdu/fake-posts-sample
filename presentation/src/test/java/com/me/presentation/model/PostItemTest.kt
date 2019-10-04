package com.me.presentation.model

import com.me.domain.usecases.CombinedUserPost
import com.me.presentation.postEntity
import com.me.presentation.userEntity
import org.junit.Assert
import org.junit.Test

class PostItemTest {

    @Test
    fun `map domain to presentation`() {
        // given

        val combinedUserPost = CombinedUserPost(userEntity, postEntity)


        // when
        val postItem = combinedUserPost.mapToPresentation()


        // then
        Assert.assertTrue(postItem.postId == postEntity.id)
        Assert.assertTrue(postItem.userId == postEntity.userId)
        Assert.assertTrue(postItem.title == postEntity.title)
        Assert.assertTrue(postItem.body == postEntity.body)
        Assert.assertTrue(postItem.name == userEntity.name)
        Assert.assertTrue(postItem.username == userEntity.username)
        Assert.assertTrue(postItem.email == userEntity.email)
    }
}