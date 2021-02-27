package com.gigaworks.tech.geticons.network.model


import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("license_id")
    val licenseId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("url")
    val url: String?
)