package com.project.common.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * A base class for Paging 3 [PagingSource].
 *
 * It simplifies the implementation of data loading from a paginated source.
 *
 * @param T The type of data to be loaded.
 */
abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {

    /**
     * The initial page index. Defaults to 1.
     */
    protected open val initialPageIndex: Int = 1

    /**
     * Executes the loading of data for a specific page.
     *
     * @param params Load parameters including the key (page index) and load size.
     * @return A [LoadResult] containing the loaded data and next/previous keys.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: initialPageIndex
        return try {
            val data = fetchData(position, params.loadSize)
            LoadResult.Page(
                data = data,
                prevKey = if (position == initialPageIndex) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    /**
     * Fetches the data for the given page and size.
     *
     * @param page The page index to load.
     * @param size The number of items to load.
     * @return A list of [T] items.
     */
    abstract suspend fun fetchData(page: Int, size: Int): List<T>

    /**
     * Returns the key to be used for the next refresh load.
     *
     * @param state The current [PagingState].
     * @return The key (page index) for the refresh.
     */
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
