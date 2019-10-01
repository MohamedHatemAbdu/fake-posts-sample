package com.me.data.entities

import com.me.data.userData
import org.junit.Test

class UserDataTest {

    @Test
    fun `map data to domain`() {
        // given

        // when
        val model = userData.mapToDomain()

        // then
        assert(model.id == userData.id)
        assert(model.name == userData.name)
        assert(model.username == userData.username)
        assert(model.email == userData.email)
    }
}