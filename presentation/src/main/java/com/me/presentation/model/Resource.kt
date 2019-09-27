package com.me.presentation.model

data class Resource<out T>(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null
)
