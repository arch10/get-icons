package com.gigaworks.tech.geticons.ui.iconsetdetails

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.network.model.toDomain
import com.gigaworks.tech.geticons.network.service.ApiService

class IconPagingSource(
    private val apiService: ApiService,
    private val iconSetId: Int
) : PagingSource<Int, Icon>() {
    override fun getRefreshKey(state: PagingState<Int, Icon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Icon> {
        val position = params.key ?: 1
        val offset = if (position == 1) 0 else ((position - 1) * params.loadSize)

        return try {
            val response = apiService.getIconsInIconSet(iconSetId, offset, params.loadSize)
            val domainResponse = response.icons.map { it.toDomain() }
            LoadResult.Page(
                data = domainResponse,
                prevKey = if (position == 1) null else (position - 1),
                nextKey = if (domainResponse.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


}