package com.example.paging3.model


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val characters: List<Character>? = null
)