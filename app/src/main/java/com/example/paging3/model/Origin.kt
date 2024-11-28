package com.example.paging3.model


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
)