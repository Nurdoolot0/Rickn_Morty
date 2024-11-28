package com.example.paging3.ui

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3.model.Character
import com.example.paging3.paging.CharactersPagingSource
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    fun getCharacters(): Flow<PagingData<Character>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {CharactersPagingSource()}
        ).flow
    }
}