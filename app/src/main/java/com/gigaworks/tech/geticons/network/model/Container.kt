package com.gigaworks.tech.geticons.network.model


import com.google.gson.annotations.SerializedName

data class Container(
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("format")
    val format: String
)