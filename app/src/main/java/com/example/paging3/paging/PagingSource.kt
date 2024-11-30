package com.example.paging3.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3.data.ApiService
import com.example.paging3.model.Character
import javax.inject.Inject

const val START_INDEX = 1

class CharactersPagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: START_INDEX
            val response = apiService.getCharacters(page = currentPage)

            if (response.isSuccessful) {
                val data = response.body()
                val nextKey = if (data?.info?.next == null) null else currentPage + 1
                val prevKey = if (data?.info?.prev == null) null else currentPage - 1

                LoadResult.Page(
                    data = data?.characters ?: emptyList(),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}