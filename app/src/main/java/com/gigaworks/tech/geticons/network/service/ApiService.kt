package com.gigaworks.tech.geticons.network.service

import com.gigaworks.tech.geticons.BuildConfig
import com.gigaworks.tech.geticons.network.response.IconResponse
import com.gigaworks.tech.geticons.network.response.IconSetResponse
import retrofit2.http.*

interface ApiService {

    companion object {
        const val AUTH_TOKEN = BuildConfig.ACCESS_TOKEN
    }

    @Headers("Authorization: Bearer $AUTH_TOKEN")
    @GET("iconsets")
    suspend fun getIconSets(
        @Query("after") after: Int?,
        @Query("count") count: Int = 20
    ) : IconSetResponse

    @Headers("Authorization: Bearer $AUTH_TOKEN")
    @GET("iconsets/{iconSet}/icons")
    suspend fun getIconsInIconSet(
        @Path("iconSet") iconSet: Int,
        @Query("offset") after: Int = 0,
        @Query("count") count: Int = 20
    ) : IconResponse

    @Headers("Authorization: Bearer $AUTH_TOKEN")
    @GET("authors/{authorId}/iconsets")
    suspend fun getIconSetByAuthor(
        @Path("authorId") authorId: Int,
        @Query("count") count: Int = 20
    ) : IconSetResponse

    @Headers("Authorization: Bearer $AUTH_TOKEN")
    @GET("icons/search")
    suspend fun searchIcons(
        @Query("query") query: String = "",
        @Query("offset") after: Int = 0,
        @Query("count") count: Int = 20
    ) : IconResponse

}