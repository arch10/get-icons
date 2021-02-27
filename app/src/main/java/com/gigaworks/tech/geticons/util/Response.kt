package com.gigaworks.tech.geticons.util

sealed class Response<out T> {
    data class Success<T>(val response: T) : Response<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val message: String?
    ) : Response<Nothing>()
}
