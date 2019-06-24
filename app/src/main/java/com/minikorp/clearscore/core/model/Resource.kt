package com.minikorp.clearscore.core.model

import androidx.lifecycle.MutableLiveData

/**
 * Simple wrapper to map ongoing tasks (network / database) to LiveData for view implementation.
 */
sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(error: Throwable?, data: T? = null) : Resource<T>(data, error)
}

fun <T : Resource<*>> MutableLiveData<T>.isErrorOrNull(): Boolean {
    return this.value == null || this.value is Error
}