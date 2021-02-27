package com.gigaworks.tech.geticons.network.response


import com.gigaworks.tech.geticons.network.model.IconSetDto
import com.google.gson.annotations.SerializedName

data class IconSetResponse(
    @SerializedName("iconsets")
    val iconsets: List<IconSetDto>
)