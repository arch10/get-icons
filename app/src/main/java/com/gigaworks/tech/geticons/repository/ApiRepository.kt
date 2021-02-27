package com.gigaworks.tech.geticons.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.network.bearer
import com.gigaworks.tech.geticons.network.model.toDomain
import com.gigaworks.tech.geticons.network.safeApiCall
import com.gigaworks.tech.geticons.network.service.ApiService
import com.gigaworks.tech.geticons.ui.home.IconSetPagingSource
import com.gigaworks.tech.geticons.ui.iconsetdetails.IconPagingSource
import com.gigaworks.tech.geticons.util.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val network: ApiService
) {

    fun getIconSets() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                IconSetPagingSource(network)
            }
        ).liveData

    fun getIconsByIconSet(iconsetId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                IconPagingSource(network, iconsetId)
            }
        ).liveData

    suspend fun getIconsInIconSet(iconsetId: Int): Response<List<Icon>> {
        return when (val networkResponse =
            safeApiCall { network.getIconsInIconSet(iconsetId, 0).icons }) {
            is Response.Failure -> {
                networkResponse
            }
            is Response.Success -> {
                val domainResponse = networkResponse.response.map { it.toDomain() }
                Response.Success(domainResponse)
            }
        }
    }

}