package com.example.whats_app_sample.data

open class Event<out T>(private val content: T) {
    var hasBeenHandled: Boolean = false

    fun getContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}