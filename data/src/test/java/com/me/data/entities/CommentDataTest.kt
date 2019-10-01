package com.me.data.entities

import com.me.data.commentData
import com.me.data.userData
import org.junit.Test

class CommentDataTest {

    @Test
    fun `map data to domain`() {
        // given

        // when
        val model = commentData.mapToDomain()

        // then
        assert(model.postId == commentData.postId)
        assert(model.id == commentData.id)
        assert(model.name == commentData.name)
        assert(model.email == commentData.email)
        assert(model.body == commentData.body)

    }
}