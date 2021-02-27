package com.gigaworks.tech.geticons.network.model

import com.gigaworks.tech.geticons.domain.Icon
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class IconDto(
    @SerializedName("is_premium")
    val isPremium: Boolean,
    @SerializedName("prices")
    val prices: List<Price>?,
    @SerializedName("icon_id")
    val iconId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("containers")
    val containers: List<Container>
)

fun IconDto.toDomain(): Icon {
    val price = if (isPremium) {
        val p: Double = prices?.get(0)?.price ?: 0.0
        val decimalFormat = DecimalFormat("0.00")
        "$${decimalFormat.format(p)}"
    } else {
        "Free"
    }
    val name = tags[0]
    val imageUrl = containers[0].downloadUrl

    return Icon(
        isPremium,
        price,
        iconId,
        type,
        name,
        imageUrl
    )
}