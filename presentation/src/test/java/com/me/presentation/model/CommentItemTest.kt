package com.me.presentation.model

import com.me.presentation.commentEntity
import org.junit.Assert
import org.junit.Test

class CommentItemTest {

    @Test
    fun `map domain to presentation`() {
        // given

        // when
        val commentItem = listOf(commentEntity).mapToPresentation().first()

        // then

        // then
        Assert.assertTrue(commentItem.postId == commentEntity.postId)
        Assert.assertTrue(commentItem.id == commentEntity.id)
        Assert.assertTrue(commentItem.name == commentEntity.name)
        Assert.assertTrue(commentItem.email == commentEntity.email)
        Assert.assertTrue(commentItem.body == commentEntity.body)
    }
}