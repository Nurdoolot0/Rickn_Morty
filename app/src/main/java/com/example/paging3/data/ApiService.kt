package com.example.paging3.data

import com.example.paging3.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page : Int
    ):BaseResponse
}