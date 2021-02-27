package com.gigaworks.tech.geticons.network.model


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("iconsets_count")
    val iconsetsCount: Int,
    @SerializedName("is_designer")
    val isDesigner: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("website_url")
    val website: String?
)