package com.example.paging3.data

import com.example.paging3.model.BaseResponse
import com.example.paging3.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<BaseResponse>

    @GET("character/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: Int
    ): Response<Character>
}