package com.example.paging3.ui

import com.example.paging3.data.ApiService
import com.example.paging3.model.Character
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDetailsRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getCharacterDetails(id: Int): Character {
        val response = apiService.getCharacterDetails(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Character not found")
        } else {
            throw Exception("Failed to load character details")
        }
    }
}