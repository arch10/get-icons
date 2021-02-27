package com.gigaworks.tech.geticons.network.response

import com.gigaworks.tech.geticons.network.model.IconDto
import com.google.gson.annotations.SerializedName

data class IconResponse(
    @SerializedName("icons")
    val icons: List<IconDto>
)