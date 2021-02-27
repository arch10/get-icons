package com.gigaworks.tech.geticons.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.network.model.toDomain
import com.gigaworks.tech.geticons.network.service.ApiService

class IconSetPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, IconSet>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IconSet> {

        val position = params.key ?: 1
        val after = if (position == 1) null else (position * params.loadSize)

        return try {
            val response = apiService.getIconSets(after, params.loadSize)
            val domainResponse = response.iconsets.map { it.toDomain() }
            LoadResult.Page(
                data = domainResponse,
                prevKey = if (position == 1) null else (position - 1),
                nextKey = if (domainResponse.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, IconSet>): Int? {
        return state.anchorPosition
    }


}