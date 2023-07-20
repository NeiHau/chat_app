package com.example.whats_app_sample.utils

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.whats_app_sample.data.Event
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val inProgress = mutableStateOf(false)

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("ChatAppClone", "Chat app exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)
        inProgress.value = false
    }
}
