package com.gigaworks.tech.geticons.network

import com.gigaworks.tech.geticons.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Response<T> {
    return withContext(Dispatchers.IO) {
        try {
            Response.Success(apiCall.invoke())
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    Response.Failure(false, e.code(), e.message)
                }
                else -> {
                    Response.Failure(true, null, e.message)
                }
            }
        }
    }
}

fun bearer(token: String): String {
    return "Bearer $token"
}