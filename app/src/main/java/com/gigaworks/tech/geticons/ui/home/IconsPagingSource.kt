package com.gigaworks.tech.geticons.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.network.model.toDomain
import com.gigaworks.tech.geticons.network.service.ApiService

class IconsPagingSource(
    private val apiService: ApiService,
    private val query: String
): PagingSource<Int, Icon>() {
    override fun getRefreshKey(state: PagingState<Int, Icon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Icon> {
        val position = params.key ?: 1
        val offset = if (position == 1) 0 else ((position - 1) * 20)

        return try {
            val response = apiService.searchIcons(query, offset, 20)
            val domainResponse = response.icons.map { it.toDomain() }
            val prevKey = if (position == 1) null else (position - 1)
            val nextKey = if (domainResponse.isEmpty()) null else position + 1
            LoadResult.Page(
                data = domainResponse,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}