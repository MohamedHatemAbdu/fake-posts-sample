package com.me.presentation.model

// TODO : to know what is the sealed class
sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}