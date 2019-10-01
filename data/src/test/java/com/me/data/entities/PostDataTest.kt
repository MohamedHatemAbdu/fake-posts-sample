package com.me.data.entities

import com.me.data.postData
import org.junit.Test

class PostDataTest {

    @Test
    fun `map data to domain`() {
        // given

        // when
        val model = postData.mapToDomain()

        // then
        assert(model.id == postData.id)
        assert(model.userId == postData.userId)
        assert(model.body == postData.body)
        assert(model.title == postData.title)
    }
}