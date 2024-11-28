package com.example.paging3.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3.data.RetrofitInstance
import com.example.paging3.model.Character

const val START_INDEX = 1

class CharactersPagingSource : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: START_INDEX
            val response = RetrofitInstance.apiService.getCharacters(page = currentPage)
            val nextKey = if (response.info?.next == null) null else currentPage + 1
            val prevKey = if (response.info?.prev == null) null else currentPage - 1

            LoadResult.Page(
                data = response.characters ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}